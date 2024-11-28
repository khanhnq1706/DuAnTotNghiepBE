package com.example.demo.service.paymentService;

import com.example.demo.config.vnpayConfig.Config;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.TableEntity;
import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.PaymentMethod;
import com.example.demo.enums.TableStatus;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.TableRepository;
import com.example.demo.respone.VNPayResponseDTO;
import com.example.demo.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VNPayService {

    @Autowired
    HttpServletRequest req;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    TableRepository tableRepository;

    public String payment(double totalPrice, String vnp_TxnRef, String bankCode) throws IOException {

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount = (long) totalPrice * 100;

        String vnp_IpAddr = Config.getIpAddress(req);

        String vnp_TmnCode = Config.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        // if(!bankCode.equals("VNPAYQR") && !bankCode.equals("VNBANK") &&
        // !bankCode.equals("INTCARD") ){
        // bankCode =null;
        // }

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        System.out.println(bankCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = req.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                // Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;

        return paymentUrl;
    }

    public VNPayResponseDTO vnpayReturn() {
        try {
            VNPayResponseDTO responseDTO = VNPayResponseDTO.builder().build();
            Map fields = new HashMap();
            for (Enumeration params = req.getParameterNames(); params.hasMoreElements();) {
                String fieldName = URLEncoder.encode((String) params.nextElement(),
                        StandardCharsets.US_ASCII.toString());
                String fieldValue = URLEncoder.encode(req.getParameter(fieldName),
                        StandardCharsets.US_ASCII.toString());
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    fields.put(fieldName, fieldValue);
                }
            }
            OrderEntity orderNeedCheck = orderRepository
                    .findById(Integer.valueOf(req.getParameter("vnp_TxnRef")))
                    .orElse(null);
            responseDTO.setBank(fields.get("vnp_BankCode").toString());
            responseDTO.setTotalAmount(fields.get("vnp_Amount").toString());
            responseDTO.setIdOrder(fields.get("vnp_TxnRef").toString());
            responseDTO.setDateTransaction(fields.get("vnp_PayDate").toString());

            String vnp_SecureHash = req.getParameter("vnp_SecureHash");
            if (fields.containsKey("vnp_SecureHashType")) {
                fields.remove("vnp_SecureHashType");
            }
            if (fields.containsKey("vnp_SecureHash")) {
                fields.remove("vnp_SecureHash");
            }
            String signValue = Config.hashAllFields(fields);
            System.out.println(fields.get("vnp_TxnRef").toString());
            if (signValue.equals(vnp_SecureHash)) {
                // Giá trị của vnp_TxnRef tồn tại trong CSDL của merchant
                boolean checkOrderId = fields.get("vnp_TxnRef").toString()
                        .equals(String.valueOf(orderNeedCheck.getIdOrder()));
                // Kiểm tra số tiền thanh toán do VNPAY phản hồi(vnp_Amount/100) với số tiền của
                // đơn hàng merchant tạo thanh toán: giả sử số tiền kiểm tra là đúng.
                boolean checkAmount = true;
                // Giả sử PaymnentStatus = 0 (pending) là trạng thái thanh toán của giao dịch
                // khởi tạo chưa có IPN.
                boolean checkOrderStatus = true;
                if (checkOrderId) {
                    if (checkAmount) {
                        if (checkOrderStatus) {
                            if ("00".equals(req.getParameter("vnp_ResponseCode"))) {
                                // Xử lý/Cập nhật tình trạng giao dịch thanh toán "Thành công"
                                orderNeedCheck.setStatusOrder(OrderStatus.Completed);
                                orderNeedCheck.setPaymentDate(new Date());
                                orderNeedCheck.setNamePaymentMethod(PaymentMethod.Ewallet.getName());
                                orderRepository.save(orderNeedCheck);
                                TableEntity table = orderNeedCheck.getTableEntity();
                                table.setStatus(TableStatus.AVAILABLE);
                                table.setCurrentOrderId(null);
                                table.setCurrentIP(null);
                                // table.setIdOrderMain(null);
                                tableRepository.save(table);
                                responseDTO.setRspCode("00");
                                String keyCheckVNPayDTO = responseDTO.getBank() + responseDTO.getTotalAmount()
                                        + responseDTO.getIdOrder() + responseDTO.getDateTransaction()
                                        + responseDTO.getRspCode();
                                responseDTO
                                        .setKeyCheck(Base64.getEncoder().encodeToString(keyCheckVNPayDTO.getBytes()));
                                responseDTO.setMessage("GD Thanh cong");
                            } else {
                                // Xử lý/Cập nhật tình trạng giao dịch thanh toán "Không thành công"
                                responseDTO.setRspCode("99");
                                responseDTO.setMessage("GD Khong Thanh cong");
                            }
                            System.out.println(responseDTO.toString());
                            System.out.println("{\"RspCode\":\"00\",\"Message\":\"Confirm Success\"}");
                        } else {
                            // Trạng thái giao dịch đã được cập nhật trước đó
                            responseDTO.setRspCode("02");
                            responseDTO.setMessage("Order already confirmed");
                        }
                    } else {
                        // Số tiền không trùng khớp
                        responseDTO.setRspCode("04");
                        responseDTO.setMessage("Invalid Amount");
                    }
                } else {
                    // Mã giao dịch không tồn tại
                    responseDTO.setRspCode("01");
                    responseDTO.setMessage("Order not Found");
                }

            } else {
                // Sai checksum
                System.out.println("{\"RspCode\":\"97\",\"Message\":\"Invalid Checksum\"}");
                responseDTO.setRspCode("07");
                responseDTO.setMessage("Invalid Checksum");
            }

            return responseDTO;

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);

        }

    }

}
