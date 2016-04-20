package com.ketnoiso.upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAutoConfiguration
@EnableScheduling
@Configuration
@ComponentScan
public class DemoApplication {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println('test 123');
        SpringApplication.run(DemoApplication.class, args);
    }
}
