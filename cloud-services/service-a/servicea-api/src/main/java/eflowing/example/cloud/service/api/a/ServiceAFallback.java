package eflowing.example.cloud.service.api.a;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class ServiceAFallback implements FallbackFactory<FeignServiceA> {
    @Override
    public FeignServiceA create(Throwable cause) {
        return new FeignServiceA() {
            @Override
            public String getName() {
                return String.format("服务A不可用，err=%s",cause.getMessage());
            }
        };
    }
}
