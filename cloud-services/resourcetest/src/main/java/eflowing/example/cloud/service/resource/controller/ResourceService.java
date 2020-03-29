package eflowing.example.cloud.service.resource.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ws")
public class ResourceService {
    @GetMapping("hello")
    @PreAuthorize("hasAnyAuthority('hello')")
    public String hello() {
        return "hello!";
    }

    @GetMapping("name")
    @PreAuthorize("hasAuthority('name')")
    public String resourceName() {
        return  "resource";
    }
}
