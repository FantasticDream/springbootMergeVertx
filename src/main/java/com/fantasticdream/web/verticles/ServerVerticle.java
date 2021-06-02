package com.fantasticdream.web.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.springframework.stereotype.Component;

/**
 * @author zc
 * @Description
 * @create 2021-06-02 10:45
 */
@Component
public class ServerVerticle extends AbstractVerticle {


    private void getHelloHandler(RoutingContext routingContext) {

        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        System.out.println(bodyAsJson);
        System.out.println("request入口");
        DeliveryOptions options = new DeliveryOptions();
        options.addHeader("content-type", "application/json");
        vertx.eventBus()
                .request("helloworld", bodyAsJson, options, result -> {
                    System.out.println("request出口");
                    if (result.succeeded()) {
                        routingContext.response()
                                .setStatusCode(200)
                                .end(result.result().body().toString());
                    } else {
                        routingContext.response()
                                .setStatusCode(500)
                                .end();
                    }
                });
    }

    @Override
    public void start() throws Exception {
        super.start();

        Router router = Router.router(vertx);
        router.route("/hello")
                .handler(BodyHandler.create())
                .handler(this::getHelloHandler);
        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8080);
    }
}
