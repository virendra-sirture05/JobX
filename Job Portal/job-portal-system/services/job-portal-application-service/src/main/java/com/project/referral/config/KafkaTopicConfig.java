//package com.project.referral.config;
//
//import org.apache.kafka.clients.admin.NewTopic;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class KafkaTopicConfig {
//
//    @Bean
//    public NewTopic applicationTopic() {
//
//        return new NewTopic(
//                "application.status.changed",
//                1,
//                (short) 1
//        );
//
//    }
//
//}