package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Value("${jwt.secretKey}")
	private String secretKey;
	
	private String[] EndPointManager = {"/api/v1/users","/api/v1/users/**","/api/QRcode/**","/api/QRcode"};
	private String[] EndPointStaff = {""};
	private String[] EndPointCustomer = {"/api/verify-table","/api/order","api/v1/foodEntities/**"
			,"/api/v1/foods/filter"};
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				(requests) ->
				requests
						.requestMatchers("/api/login").permitAll()
						.requestMatchers(EndPointCustomer).permitAll()
						.requestMatchers(EndPointManager).hasAuthority("SCOPE_MANAGER")
						.anyRequest().authenticated()
		);
		http.oauth2ResourceServer( oauth2 -> oauth2.jwt(jwtConfig -> jwtConfig.decoder(jwtDecoder())) );
		http.csrf(csrf->csrf.disable());

		return http.build();
	}
	
	@Bean
	public JwtDecoder jwtDecoder() {
		
		SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "SH512");
			
		return 	NimbusJwtDecoder.withSecretKey(secretKeySpec)
				.macAlgorithm(MacAlgorithm.HS512)
				.build();
	}

}
