package com.project.referral.config;

import feign.RequestInterceptor;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignCookieConfig {

    @Bean
    public RequestInterceptor cookieForwardingInterceptor() {

        return requestTemplate -> {

            ServletRequestAttributes attributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if (attributes == null) {
                return;
            }

            HttpServletRequest request = attributes.getRequest();

            Cookie[] cookies = request.getCookies();

            if (cookies == null) {
                return;
            }

            StringBuilder cookieHeader = new StringBuilder();

            for (Cookie cookie : cookies) {
                System.out.println("Forwarding Cookie: "
                        + cookie.getName() + "=" + cookie.getValue());
                if (cookieHeader.length() > 0) {
                    cookieHeader.append("; ");
                }

                cookieHeader
                        .append(cookie.getName())
                        .append("=")
                        .append(cookie.getValue());
            }
            System.out.println("Cookie Header: " + cookieHeader);
            requestTemplate.header("Cookie", cookieHeader.toString());
        };
    }
}