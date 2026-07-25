package com.project.referrer;

import com.project.referral.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest // Tells Spring to load the application context and enable @Autowired
class JwtServiceTest {

	@Autowired
	private JwtService jwtService;

	@Test
	void generateToken() {
		String access = jwtService.generateAccessToken("admin@jobx.com");
		String refresh = jwtService.generateRefreshToken("admin@jobx.com");

		System.out.println("Access Token: " + access);
		System.out.println("Refresh Token: " + refresh);
	}
}
