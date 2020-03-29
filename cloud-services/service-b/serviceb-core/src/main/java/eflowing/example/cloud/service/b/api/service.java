package eflowing.example.cloud.service.b.api;

import eflowing.example.cloud.service.api.a.IServiceA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("ws")
public class service {
    @Autowired
    private IServiceA aservice;

    @GetMapping("aska")
    private String getOtherServer() {
        return String.format("What's your name? \r\n %s", aservice.getName());
    }
}
