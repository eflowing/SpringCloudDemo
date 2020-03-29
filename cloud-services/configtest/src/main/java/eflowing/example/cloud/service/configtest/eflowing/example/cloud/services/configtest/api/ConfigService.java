package eflowing.example.cloud.service.configtest.eflowing.example.cloud.services.configtest.api;

import eflowing.example.cloud.service.configtest.configs.MyConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/config")
@Slf4j
@RefreshScope
public class ConfigService {
    @Autowired
    MyConfig cfg;

    @Value("${spring.profiles}")
    private String profiles;

    @Value("${spring.datasource.type}")
    private String dataSourceType;

    @Value("${spring.sleuth.sampler.probability}")
    private String sssp;


    @Value("${spring.check}")
    private String checkProfile;
//
//    @Value("${spring.src}")
//    private String src;

    @GetMapping("mycfg")
    public MyConfig getCfg() {
        return cfg;
    }

    @GetMapping("profile")
    public String getProfile() {
        return profiles;
    }

//    @GetMapping("datasrc")
//    public String getDataSrc(){
//        return src;
//    }

    @GetMapping("sssp")
    public String sssp(){
        return sssp;
    }

    @RefreshScope
    @GetMapping("checkProfile")
    public String checkProfile(){
        return checkProfile;
    }

    @GetMapping("myprofile")
    public String getMyProfile() {
        return cfg.getProfile();
    }

    @GetMapping("client")
    public MyConfig.Client getClient() {
        return cfg.getClient();
    }

    @GetMapping("user")
    public MyConfig.User getUser() {
        return cfg.getUser();
    }

    @GetMapping("datasourcetype")
    public String getDataSourceType() {
        return dataSourceType;
    }
}
