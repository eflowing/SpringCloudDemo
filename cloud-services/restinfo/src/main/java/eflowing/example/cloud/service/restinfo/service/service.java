package eflowing.example.cloud.service.restinfo.service;

import eflowing.example.cloud.service.restinfo.entity.Person;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("ws/rest")
public class service {
    @GetMapping("name")
    @PreAuthorize("hasAuthority('name')")
    public String getServiceName(){
        return "my name is restinfo service.";
    }

    @GetMapping("type")
    public String getServiceType(){
        return "check type";
    }

    @GetMapping("validate")
    public String validate(@Valid @RequestBody Person person){
        return "validate" + person;
    }
}
