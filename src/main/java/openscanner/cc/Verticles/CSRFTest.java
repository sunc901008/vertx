package openscanner.cc.Verticles;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.*;
import openscanner.handlebars.HandlebarsTemplateEngine;

import java.util.Set;

public class CSRFTest {

    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);

        router.route().handler(CookieHandler.create());
        router.route().handler(BodyHandler.create());

        router.route().handler(CSRFHandler.create("secret"));
        router.route().handler(FaviconHandler.create("webroot/favicon.ico"));

        router.route("/static/*").handler(StaticHandler.create());
//        router.route("/favicon.ico").handler(ctx -> {
//            System.out.println("static");
//            ctx.reroute("/static/favicon.ico");
//        });

        router.route().handler(ctx -> {
            System.out.println("root");
            Set<Cookie> set = ctx.cookies();
            set.forEach(c -> System.out.println(c.getName() + ":" + c.getValue()));
            String csrf = ctx.getCookie(CSRFHandler.DEFAULT_COOKIE_NAME).getValue();
            ctx.put("csrf", csrf);
            ctx.next();
        });



        router.route().failureHandler(ctx -> {
            System.out.println("faile");
            ctx.failure().printStackTrace();
            ctx.response().end(ctx.statusCode() + "");
        });

        router.get("/").handler(ctx -> {
            HandlebarsTemplateEngine.create().render(ctx, "templates/login.hbs", res -> {
                if (res.succeeded()) {
                    System.out.println("templates");
                    ctx.response().end(res.result());
                } else {
                    res.cause().printStackTrace();
                    ctx.fail(500);
                }
            });
        });

        router.post("/test").handler(ctx -> {
            System.out.println("test");
            String params = ctx.request().params().toString();
            ctx.response().end(params);
        });

        server.requestHandler(router::accept).listen(8090, "localhost", res -> {
            if (res.succeeded())
                System.out.println("success");
        });
    }
}
