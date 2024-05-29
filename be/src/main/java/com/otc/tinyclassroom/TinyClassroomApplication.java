package com.otc.tinyclassroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * main Application.
 */
@SpringBootApplication
public class TinyClassroomApplication {

    static {
        System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
    }

    public static void main(String[] args) {
        SpringApplication.run(TinyClassroomApplication.class, args);
    }
}
