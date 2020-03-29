package eflowing.example.cloud.service.resource.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "resource.ignore")
public class ResourceIgnoreUrls {
    private List<String> urls;

    public ResourceIgnoreUrls(){
        urls = new ArrayList<>();
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
