package eflowing.example.cloud.service.a;

import eflowing.example.cloud.service.api.b.FeignServiceB;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableHystrix
@EnableFeignClients(clients = {FeignServiceB.class})
//@EnableFeignClients(basePackages = {"eflowing.example.cloud.service.api.b"})
//@SpringBootApplication(scanBasePackages = {"eflowing.example.cloud.service.a","eflowing.example.cloud.service.api.b"})
@SpringBootApplication
public class ServiceAApplication {

	public static void main(String[] args)	 {
		SpringApplication.run(ServiceAApplication.class, args);
	}
}
