package com.attornatus.backend.utils;

import com.attornatus.backend.AvaliacaoDesenvolvedorBackendApplication;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AvaliacaoDesenvolvedorBackendApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class IntegrationTestConfig {

    protected static final Pattern UUID_PATTERN = Pattern.compile("[^/]+$");

    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    protected String getIdHeaderLocation(Response response) {
        String location = response.getHeader("Location");
        Matcher matcher = UUID_PATTERN.matcher(location);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

}