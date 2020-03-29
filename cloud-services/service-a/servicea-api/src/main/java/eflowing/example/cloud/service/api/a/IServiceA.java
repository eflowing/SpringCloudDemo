package eflowing.example.cloud.service.api.a;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("a/api")
public interface IServiceA {
    @GetMapping("name")
    String getName();
}
