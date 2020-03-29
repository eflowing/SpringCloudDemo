package eflowing.example.cloud.service.redis.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/config")
public class ConfigService {
    @Value("${spring.datasource.url}")
    private String dataSource;

    @Value("${spring.redis.port}")
    private String redisPort;

    @GetMapping("data/source/url")
    public String GetDataSourceUrl() {
        return dataSource;
    }

    @GetMapping("data/redis/port")
    public String GetRedisPort() {
        return redisPort;
    }
}
