package com.example.demo.respone;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor
@NoArgsConstructor @Builder

public class VNPayResponseDTO {
    private String RspCode;
    private String Message;
    private String urlToRedirect;

}
