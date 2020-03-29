package eflowing.example.cloud.service.configtest.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "eflowing")
public class MyConfig {
    private String profile;
    private Client client;
    private User user;

    @Data
    public static class Client {
        private String id;
        private String name;
    }

    @Data
    public static class User {
        private String id;
        private String name;
        private String mobile;
    }
}
