package eflowing.example.cloud.service.api.b;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("b/api")
public interface IServiceB {
    @GetMapping("name")
    String getName();
}