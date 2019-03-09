package com.zfoo.web.flux.facade;

import com.zfoo.web.flux.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.*;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class UserRouterController {

    private final List<User> users = new ArrayList<>();

    private final Flux<User> userStream = Flux.interval(Duration.ofMillis(1000))
            // In case of back-pressure, drop events
            .onBackpressureDrop()
            // For each tick, generate a list of quotes
            .map(n -> generateUsers())
            // "flatten" that List<Quote> into a Flux<Quote>
            .flatMapIterable(users -> users)
            .log("com.zfoo.flux")
            .share();

    public UserRouterController() {
        users.add(new User(1, "Jack", "Smith", 20));
        users.add(new User(2, "Peter", "Johnson", 25));
    }

    /**
     * 不断地变换年龄
     */
    private List<User> generateUsers() {
        return users.stream().peek(user -> user.setAge(new Random().nextInt())).collect(Collectors.toList());
    }

    // localhost:8080/user/router/hello
    public Mono<ServerResponse> hello(ServerRequest request) {
        return ok().contentType(TEXT_PLAIN)
                .body(BodyInserters.fromObject("Hello Spring!"));
    }

    // localhost:8080/user/router/echo
    public Mono<ServerResponse> echo(ServerRequest request) {
        return ok().contentType(TEXT_PLAIN)
                .body(request.bodyToMono(String.class), String.class);
    }

    // localhost:8080/user/router/stream
    public Mono<ServerResponse> streamUser(ServerRequest request) {
        return ok()
                .contentType(APPLICATION_JSON)
                .body(this.userStream, User.class);
    }

    // localhost:8080/user/router/stream-json
    public Mono<ServerResponse> fetchUser(ServerRequest request) {
        int size = Integer.parseInt(request.queryParam("size").orElse("10"));
        return ok()
                .contentType(APPLICATION_STREAM_JSON)
                .body(this.userStream.take(size), User.class);
    }

}
