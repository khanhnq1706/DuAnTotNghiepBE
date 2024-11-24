package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


	@Bean
	public  PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder(10);
	}

	@Value("${jwt.secretKey}")
	private String secretKey;

	private JwtDecoderCustom jwtDecoder;

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	private String[] EndPointManager = {"/api/v1/users","/api/v1/users/**","/api/QRcode/**","/api/QRcode",
			};
	private String[] EndPointPublic = {"/api/v1/categories","/api/verify-table","/api/v1/order","/api/v1/order/**"
			,"api/v1/foodEntities/**","/api/order","/api/order/**","api/v1/orderdetail","api/v1/orderdetail/**"
			,"/api/v1/foods/filter","/api/login","/api/logout","/api/testVerify","/api/v1/ip","/api/v1/ip/**"
			,"/ws/my-websocket-endpoint","/ws/my-websocket-endpoint/**","/images/**","/QRCode/**","api/payment-VNPay"
	,"/api/v1/foods/**","/api/v1/foods"};
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				(requests) ->
				requests
						.requestMatchers(EndPointPublic).permitAll()
						.requestMatchers(EndPointManager).hasAuthority("SCOPE_MANAGER")
						.anyRequest().authenticated()
		);
		http.oauth2ResourceServer( oauth2 -> oauth2
											.jwt(jwtConfig -> jwtConfig.decoder(jwtDecoder))
											.authenticationEntryPoint(jwtAuthenticationEntryPoint));
		http.csrf(csrf->csrf.disable());

		return http.build();
	}


}
