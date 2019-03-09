package com.zfoo.web.flux.facade;

import com.zfoo.web.flux.model.User;
import com.zfoo.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private Map<Long, User> users = new HashMap<>();

    public UserController() {
        users.put(1L, new User(1, "Jack", "Smith", 20));
        users.put(2L, new User(2, "Peter", "Johnson", 25));
    }

    // Flux和Mono 是 Reactor 中的流数据类型，其中Flux会发送多次，Mono会发送0次或一次
    // 获取所有用户
    // localhost:8080/user/index
    @GetMapping("/index")
    public Flux<User> getAll() {
        return Flux.fromIterable(users.entrySet().stream()
            .map(entry -> entry.getValue())
            .collect(Collectors.toList()));
    }

    // 获取单个用户
    // localhost:8080/user/1
    @GetMapping("/{id}")
    public Mono<String> getCustomer(@PathVariable Long id) {
        logger.debug("########### GET:" + users.get(id));
        return Mono.justOrEmpty(JsonUtils.object2String(users.get(id)));
    }


    // 创建用户
    // localhost:8080/user/post
    @PostMapping("/post")
    public Mono<ResponseEntity<String>> postUser(@RequestBody User user) {
        users.put(user.getId(), user);
        logger.info("########### POST:" + user);
        return Mono.just(new ResponseEntity<>("Post Successfully!", HttpStatus.CREATED));
    }


    // 修改用户
    // localhost:8080/user/put/1
    @PutMapping("/put/{id}")
    public Mono<ResponseEntity<User>> putCustomer(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        users.put(id, user);
        logger.debug("########### PUT:" + user);
        return Mono.just(new ResponseEntity<>(user, HttpStatus.CREATED));
    }

    // 删除用户
    // localhost:8080/user/delete/1
    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<String>> deleteMethod(@PathVariable Long id) {
        users.remove(id);
        return Mono.just(new ResponseEntity<>("Delete Successfully!", HttpStatus.ACCEPTED));
    }
}
