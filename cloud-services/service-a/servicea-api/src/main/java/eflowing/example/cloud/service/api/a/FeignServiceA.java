package eflowing.example.cloud.service.api.a;

import org.springframework.cloud.openfeign.FeignClient;

//@FeignClient(name = "SERVICE-A", fallbackFactory = ServiceAFallback.class)
@FeignClient(name = "SERVICE-A")
public interface FeignServiceA extends IServiceA {
}
