package com.zfoo.web.river;

import com.zfoo.web.river.config.GracefulShutdownConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2019-03-10 19:43
 */
@SpringBootApplication
@ImportResource(locations = {"event.xml", "storage.xml", "scheduler.xml"})
public class RiverApplication {

    private static final Logger logger = LoggerFactory.getLogger(RiverApplication.class);

    @Autowired
    private GracefulShutdownConfig gracefulShutdownTomcat;

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addConnectorCustomizers(gracefulShutdownTomcat);
        return tomcat;
    }


    public static void main(String[] args) {
        SpringApplication.run(RiverApplication.class, args);
        logger.info("Start River Application!");
    }

}
