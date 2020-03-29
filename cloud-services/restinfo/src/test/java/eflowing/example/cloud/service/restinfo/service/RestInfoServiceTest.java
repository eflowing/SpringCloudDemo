package eflowing.example.cloud.service.restinfo.service;

import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class RestInfoServiceTest {

    @Test
    public void getAllRequestMappingInfo() {
        Pattern pattern=Pattern.compile("(男|女)");
        System.out.println("result: " + pattern.matcher("男").toString());
    }

    @Test
    public void list() {
    }
}