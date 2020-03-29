package eflowing.example.cloud.service.b.api;

import eflowing.example.cloud.service.api.b.IServiceB;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignService implements IServiceB {
    @Value("${eureka.instance.instance-id}")
    private String instance;

    @Override
    public String getName() {
        return String.format("I'm B. instance is %s", instance);
    }
}
