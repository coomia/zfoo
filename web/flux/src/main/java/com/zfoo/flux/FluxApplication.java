package com.zfoo.flux;

import com.zfoo.flux.config.ErrorPageConfig;
import com.zfoo.flux.facade.UserRouterController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

/*
@SpringBootApplication 是 Spring Boot 的核心注解，它是一个组合注解，该注解组合了：
@Configuration、@EnableAutoConfiguration、@ComponentScan；
若不是用 @SpringBootApplication 注解也可以使用这三个注解代替。

其中，@EnableAutoConfiguration 让 Spring Boot 根据类路径中的 jar 包依赖为当前项目进行自动配置，
例如，添加了 spring-boot-starter-web 依赖，会自动添加 Tomcat 和 Spring MVC 的依赖，那么 Spring Boot 会对 Tomcat 和 Spring MVC 进行自动配置。
*/
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class FluxApplication {

    @Bean
    public ErrorPageConfig errorPageRegistrar() {
        return new ErrorPageConfig();
    }

    @Bean
    public RouterFunction<ServerResponse> route(UserRouterController userRouterHandler) {
        return RouterFunctions
                .route(GET("/user/router/hello").and(accept(TEXT_PLAIN)), userRouterHandler::hello)
                .andRoute(POST("/user/router/echo").and(accept(TEXT_PLAIN).and(contentType(TEXT_PLAIN))), userRouterHandler::echo)
                .andRoute(GET("/user/router/stream").and(accept(APPLICATION_JSON)), userRouterHandler::streamUser)
                .andRoute(GET("/user/router/stream-json").and(accept(APPLICATION_STREAM_JSON)), userRouterHandler::fetchUser);
    }

    public static void main(String[] args) {
        SpringApplication.run(FluxApplication.class, args);
    }

}
