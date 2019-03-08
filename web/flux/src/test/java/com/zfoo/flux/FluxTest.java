package com.zfoo.flux;

import com.zfoo.flux.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.nio.charset.Charset;
import java.util.List;

public class FluxTest {


    /*
    @Test
    public void test() {
        WebTestClient.ResponseSpec response = webTestClient.get().uri("/user/index")
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(Charset.forName("utf-8"))
                .exchange();

        List<User> list = response.expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .returnResult(User.class)
                .getResponseBody()
                .take(30)
                .collectList()
                .block();
        System.out.println(list);
    }

    @Test
    public void streamTest() {
        List<User> result = webTestClient
                // We then create a GET request to test an endpoint
                .get().uri("/user/router/stream-json")
                // this time, accepting "application/stream+json"
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                // and use the dedicated DSL to test assertions against the response
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_STREAM_JSON)
                .returnResult(User.class)
                .getResponseBody()
                .take(30)
                .collectList()
                .block();

        System.out.println(result);
    }
    */

}
