package com.project_referral.config;

import org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class RouteConfig {
    @Bean
    public RouterFunction<ServerResponse> preferenceServiceRoutes() {
        return GatewayRouterFunctions.route("preference-service-routes")
                .route(RequestPredicates.path("/api/preferences/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("job-portal-preferences"))
//                .before(this::jwtAuthFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> companyServiceRoutes(){
        return GatewayRouterFunctions.route("company-service-routes")
                .route(RequestPredicates.path("/api/companies/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("job-portal-company-service"))
//                .before(this::jwtAuthFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> jobServiceRoutes() {
        return GatewayRouterFunctions.route("job-service-routes")
                .route(RequestPredicates.path("/api/jobs/**")
                                .or(RequestPredicates.path("/api/job-categories/**"))
                                .or(RequestPredicates.path("/api/job-skills/**"))
                                .or(RequestPredicates.path("/api/job-tags/**")),
                        HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("job-portal-job-service"))
//                .before(this::jwtAuthFilter)
                .build();
    }

}

