package com.class7no.springbootmybatisyml;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * springboot整合mybatis yml
 */
@SpringBootApplication
@MapperScan(value = "com.....mapper")
public class SpringbootMybatisYmlApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMybatisYmlApplication.class, args);
    }

}
