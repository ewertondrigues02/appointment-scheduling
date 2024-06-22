package br.com.ewerton.serviceschedules;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableDiscoveryClient
@SpringBootApplication
public class ServiceSchedulesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceSchedulesApplication.class, args);
    }

}
