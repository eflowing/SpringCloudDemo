package eflowing.example.cloud.service.resource.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserService {
    @GetMapping("login")
    public String login() {
        return "login";
    }

}
