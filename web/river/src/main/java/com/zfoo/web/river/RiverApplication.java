package com.zfoo.web.river;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations = {"event.xml", "storage.xml", "scheduler.xml"})
public class RiverApplication {

    private static final Logger logger = LoggerFactory.getLogger(RiverApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(RiverApplication.class, args);
        logger.info("Start River Application!");
    }

}
