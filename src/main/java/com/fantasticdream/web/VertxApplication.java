package com.fantasticdream.web;

import com.fantasticdream.web.verticles.HelloWorldVerticle;
import com.fantasticdream.web.verticles.ServerVerticle;
import io.vertx.core.Vertx;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author zc
 * @Description
 * @create 2021-06-02 10:41
 */

@SpringBootApplication
public class VertxApplication {

    @Resource
    ServerVerticle serverVerticle;

    @Resource
    HelloWorldVerticle helloWorldVerticle;

    public static void main(String[] args) {
        SpringApplication.run(VertxApplication.class);
    }

    @PostConstruct
    public void deployVerticle(){
        final Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(serverVerticle);
        vertx.deployVerticle(helloWorldVerticle);
    }
}
