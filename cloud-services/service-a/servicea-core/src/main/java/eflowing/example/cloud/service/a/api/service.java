package eflowing.example.cloud.service.a.api;

import eflowing.example.cloud.service.api.b.IServiceB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("ws")
public class service {
    @Autowired
    private IServiceB bservice;

    @GetMapping("askb")
    private String getOtherServer() {
        return String.format("What's your name? \r\n %s", bservice.getName());
    }
}
