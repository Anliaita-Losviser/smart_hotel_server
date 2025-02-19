package com.smartHotel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement //开启注解方式的事务管理
@Slf4j
public class SmartHotelApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmartHotelApplication.class, args);
        log.info("server started");
    }
}
