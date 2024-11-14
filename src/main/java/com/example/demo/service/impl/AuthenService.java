package com.example.demo.service.impl;


import com.example.demo.entity.UserEnitty;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.AuthenRequest;
import com.example.demo.respone.ApiRespone;
import com.example.demo.respone.AuthenRespone;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class AuthenService {

	@Autowired
	UserRepository userRepository;

	@Value("${jwt.secretKey}")
	private String secretKey;

	public ApiRespone<AuthenRespone> authenAccount(AuthenRequest authRequest) {

		UserEnitty userNeedAuthen = userRepository.findByUsername(authRequest.getUsername());

		PasswordEncoder pe = new BCryptPasswordEncoder(10);		
		if (userNeedAuthen == null) {
			throw new RuntimeException("USER_NOT_EXISTS");
		}
		if (userNeedAuthen.getIsDeleted()) {
			throw new RuntimeException("Deleted_USER");
		}
		
		if (!pe.matches(authRequest.getPassword(), userNeedAuthen.getPassword())) {
			System.out.println(pe.encode(userNeedAuthen.getPassword()));
			throw new RuntimeException("WRONG_PASSWORD");
		}
		ApiRespone<AuthenRespone> respone = new ApiRespone<AuthenRespone>();
		AuthenRespone authRespone = new AuthenRespone();
		String token = generateToken(userNeedAuthen);

		authRespone.setAuthen(true);
		authRespone.setToken(token);
		respone.setResult(authRespone);

		return respone;
	}

	private String generateToken(UserEnitty userNeedAuthen) {

		JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

		JWTClaimsSet claim = new JWTClaimsSet.Builder().issueTime(new Date())
				.expirationTime(new Date(Instant.now().plusSeconds(60 * 60 * 24).toEpochMilli()))
				.claim("scope", userNeedAuthen.getIsAdmin() ? "MANAGER" : "STAFF")
				.claim("ID", userNeedAuthen.getIdUser())
				.subject(userNeedAuthen.getUsername()).build();

		Payload payload = new Payload(claim.toJSONObject());

		JWSObject jwsObject = new JWSObject(header, payload);

		try {
			jwsObject.sign(new MACSigner(secretKey.getBytes()));
			return jwsObject.serialize();
		} catch (JOSEException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("chua biet ");
		}

	}

}
