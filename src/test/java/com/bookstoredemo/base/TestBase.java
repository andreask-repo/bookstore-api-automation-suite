package com.bookstoredemo.base;

import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.restassured.RestAssured.baseURI;

public class TestBase {

    @BeforeAll
    public static void globalSetup() {
        // Load properties from config file
        Properties properties = new Properties();
        try (InputStream input = TestBase.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Cannot find config.properties");
                return;
            }

            properties.load(input);
            baseURI = properties.getProperty("base.url");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
