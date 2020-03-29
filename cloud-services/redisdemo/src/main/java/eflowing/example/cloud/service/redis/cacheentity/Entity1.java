package eflowing.example.cloud.service.redis.cacheentity;

import eflowing.example.cloud.service.redis.api.Person;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@CacheConfig(cacheNames = "demo")
@Component
public class Entity1 {
    @Cacheable(key = "'vl'")
    public long getValue() {
        return 10;
    }

    @CachePut(key = "'vl'")
    public long setValue(long value) {
        return value;
    }

    @Cacheable(key = "#id")
    public String getUser(int id) {
        return "none";
    }

    @CachePut(key = "#id")
    public String setUser(int id, String name) {
        return name;
    }

    @Cacheable(key = "'P001'")
    public String getPerson() {
        return "json";
    }

    @CachePut(key = "'P001'")
    public String setPerson(String name) {
        return name;
    }
}
