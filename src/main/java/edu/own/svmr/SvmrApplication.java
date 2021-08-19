package edu.own.svmr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "edu.own.svmr.mapper")
public class SvmrApplication {

    public static void main(String[] args) {
        SpringApplication.run(SvmrApplication.class, args);
    }

}
