package com.project_referral.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        long start = System.currentTimeMillis();

        ContentCachingRequestWrapper wrappedRequest =
                new ContentCachingRequestWrapper(request,1024 * 1024);

        try {

            filterChain.doFilter(wrappedRequest, response);

        } finally {

            log.info("====================================================");
            log.info("Incoming Request");

            log.info("Method : {}", wrappedRequest.getMethod());

            log.info("URI    : {}", wrappedRequest.getRequestURI());

            log.info("URL    : {}", wrappedRequest.getRequestURL());

            log.info("Query  : {}", wrappedRequest.getQueryString());

            log.info("Remote IP : {}", wrappedRequest.getRemoteAddr());

            log.info("---------------- Headers ----------------");

            Enumeration<String> headerNames = wrappedRequest.getHeaderNames();

            while (headerNames.hasMoreElements()) {

                String header = headerNames.nextElement();

                log.info("{} : {}", header, wrappedRequest.getHeader(header));
            }

            log.info("---------------- Parameters ----------------");

            wrappedRequest.getParameterMap()
                    .forEach((k, v) ->
                            log.info("{} = {}", k, String.join(",", v)));

            byte[] body = wrappedRequest.getContentAsByteArray();

            if (body.length > 0) {

                String requestBody =
                        new String(body, StandardCharsets.UTF_8);

                log.info("---------------- Body ----------------");
                log.info(requestBody);

            } else {

                log.info("Body : Empty");
            }

            log.info("---------------- Response ----------------");

            log.info("Status : {}", response.getStatus());

            log.info("Time   : {} ms",
                    System.currentTimeMillis() - start);

            log.info("====================================================");
        }
    }
}