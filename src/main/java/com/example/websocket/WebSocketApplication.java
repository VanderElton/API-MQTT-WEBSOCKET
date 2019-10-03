package com.example.websocket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Arrays;

@SpringBootApplication
@EnableAsync
public class WebSocketApplication {

    private static final Log LOG = LogFactory.getLog(WebSocketApplication.class);

    public static void main(String[] args) {

        LOG.info("WEG IoT application started " + Arrays.toString(args));
        SpringApplication.run(WebSocketApplication.class, args);

    }

}
