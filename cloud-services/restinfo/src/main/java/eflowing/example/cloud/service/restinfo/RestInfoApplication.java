package eflowing.example.cloud.service.restinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
public class RestInfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestInfoApplication.class, args);
    }
}
