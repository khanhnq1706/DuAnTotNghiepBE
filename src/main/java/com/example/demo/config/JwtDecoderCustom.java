    package com.example.demo.config;

    import com.example.demo.service.impl.AuthenService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
    import org.springframework.security.oauth2.jwt.Jwt;
    import org.springframework.security.oauth2.jwt.JwtDecoder;
    import org.springframework.security.oauth2.jwt.JwtException;
    import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
    import org.springframework.stereotype.Component;
    import org.springframework.stereotype.Service;

    import javax.crypto.spec.SecretKeySpec;

    @Service
    public class JwtDecoderCustom implements JwtDecoder {

        @Value("${jwt.secretKey}")
        private String secretKey;

        @Autowired
        private AuthenService authenService;


        @Override
        public Jwt decode(String token) throws JwtException {
            try {
                if(!authenService.verifyToken(token)){
                    throw new RuntimeException("OLD_TOKEN");
                }
            } catch (Exception e){
                throw new RuntimeException("OLD_TOKEN");
            }

            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HS512");
            JwtDecoder jwtDecoder = NimbusJwtDecoder
                    .withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();

            return jwtDecoder.decode(token);
        }

//        @Override
//        public Jwt decode(String token) throws JwtException {
//            try {
//                if (!authenService.verifyToken(token)) {
//                    throw new RuntimeException("OLD_TOKEN");
//                }
//            } catch (Exception e) {
//                // Thêm log WARN tại đây
//                throw new RuntimeException("OLD_TOKEN");
//            }
//
//            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HS512");
//            JwtDecoder jwtDecoder = NimbusJwtDecoder
//                    .withSecretKey(secretKeySpec)
//                    .macAlgorithm(MacAlgorithm.HS512)
//                    .build();
//
//            return jwtDecoder.decode(token);
//        }


    }
