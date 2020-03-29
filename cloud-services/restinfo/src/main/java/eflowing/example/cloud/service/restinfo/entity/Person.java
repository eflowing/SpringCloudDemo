package eflowing.example.cloud.service.restinfo.entity;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Data
public class Person {
    @NotNull
    private String name;

    @Min(1)
    @Max(200)
    private int age;

    @Pattern(regexp = "(男|女)",message = "性别必须为‘男’或‘女’")
    private String sex;
}
