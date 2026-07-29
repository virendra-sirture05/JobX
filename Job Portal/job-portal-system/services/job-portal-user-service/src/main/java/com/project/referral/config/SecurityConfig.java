package com.project.referral.config;


import com.project.referral.security.CustomUserDetailsService;
import com.project.referral.filter.CorrelationIdFilter;
import com.project.referral.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final CustomUserDetailsService customUserDetailsService;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final CorrelationIdFilter correlationIdFilter;

    @Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration configuration)
			throws Exception {

		return configuration.getAuthenticationManager();

	}

	
	 @Bean
	    SecurityFilterChain securityFilterChain(HttpSecurity http)
	            throws Exception {
	        http.csrf(csrf -> csrf.disable())
					.authorizeHttpRequests(auth -> auth
							.requestMatchers("/api/auth/**","/actuator/health/**", "/actuator/info").permitAll()
							.anyRequest().authenticated()

	            )
					.sessionManagement(session ->

							session.sessionCreationPolicy(
									SessionCreationPolicy.STATELESS
							)

					)
				 .addFilterBefore(correlationIdFilter, UsernamePasswordAuthenticationFilter.class)
				 .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

	        return http.build();

	    }
}
