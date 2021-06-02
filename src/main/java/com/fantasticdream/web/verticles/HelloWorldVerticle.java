package com.fantasticdream.web.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import org.springframework.stereotype.Component;

/**
 * @author zc
 * @Description
 * @create 2021-06-02 13:51
 */
@Component
public class HelloWorldVerticle extends AbstractVerticle {


    @Override
    public void start() throws Exception {
        super.start();
        vertx.eventBus()
                .consumer("helloworld",helloWorldConsume());
    }


    public Handler<Message<String>> helloWorldConsume() {
        return msg -> vertx.executeBlocking(future -> {
            System.out.println(JsonObject.mapFrom(msg.body()));
            future.complete(returnHello());
        }, result -> {
            if(result.succeeded()){
                msg.reply(result.result());
            }else {
                msg.reply("fail");
            }
            System.out.println(result);

        });
    }


    public String returnHello(){
        return "hello";
    }
}
