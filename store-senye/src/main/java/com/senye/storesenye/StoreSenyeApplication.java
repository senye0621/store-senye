package com.senye.storesenye;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.senye.storesenye.mapper")
public class StoreSenyeApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreSenyeApplication.class, args);
    }

}
