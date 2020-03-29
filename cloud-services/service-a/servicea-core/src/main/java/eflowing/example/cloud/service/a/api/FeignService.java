package eflowing.example.cloud.service.a.api;

import eflowing.example.cloud.service.api.a.IServiceA;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignService implements IServiceA {
    @Value("${eureka.instance.instance-id}")
    private String instance;

    @Override
    public String getName() {
        return String.format("I'm A. instance is %s", instance);
    }
}
