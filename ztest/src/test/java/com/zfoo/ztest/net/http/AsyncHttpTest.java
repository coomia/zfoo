package com.zfoo.ztest.net.http;

import com.zfoo.util.IOUtils;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.Response;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018-09-12 20:17
 */

@Ignore
public class AsyncHttpTest {

    @Test
    public void test() {
        AsyncHttpClient asyncHttpClient = null;
        try {
            asyncHttpClient = Dsl.asyncHttpClient();

            // usage1
            asyncHttpClient
                    .prepareGet("http://www.baidu.com/")
                    .execute()
                    .toCompletableFuture()
                    .thenApply(Response::getResponseBody)
                    .thenAccept(System.out::println)
                    .join();

            // usage2
//            CompletableFuture<Response> whenResponse = asyncHttpClient
//                    .prepareGet("http://www.example.com/")
//                    .execute()
//                    .toCompletableFuture()
//                    .exceptionally(t -> { /* Something wrong happened... */  } )
//                    .thenApply(response -> { /*  Do something with the Response */ return resp; });
//            whenResponse.join(); // wait for completion
        } finally {
            IOUtils.closeIO(asyncHttpClient);
        }
    }

}
