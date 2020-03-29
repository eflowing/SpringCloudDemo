package eflowing.example.cloud.service.redis.api;

import lombok.Data;

@Data
public class Person {
    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private String name;
    private int age;
}
