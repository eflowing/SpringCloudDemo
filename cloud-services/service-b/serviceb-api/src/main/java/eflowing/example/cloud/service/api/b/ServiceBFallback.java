package eflowing.example.cloud.service.api.b;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class ServiceBFallback implements FallbackFactory<FeignServiceB> {
    @Override
    public FeignServiceB create(Throwable cause) {
        return new FeignServiceB() {
            @Override
            public String getName() {
                return String.format("服务B不可用，err=%s",cause.getMessage());
            }
        };
    }
}
