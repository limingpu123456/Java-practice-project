package com.example.onlinemusic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})

public class MusicserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicserverApplication.class, args);
    }

}
