package eflowing.example.cloud.service.api.b;

import org.springframework.cloud.openfeign.FeignClient;

//@FeignClient(name = "SERVICE-B", fallbackFactory = ServiceBFallback.class)
@FeignClient(name = "SERVICE-B")
public interface FeignServiceB extends IServiceB {
}