package openscanner.cc.session;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import openscanner.cc.handler.RootHandler;

/**
 * Created : sunc
 * Date : 16-6-17
 * Description :
 */
public class SessoinTest1 extends AbstractVerticle {

    @Override
    public void start() {

        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        router.route().handler(CookieHandler.create());
        router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)).setSessionTimeout(10000));

        router.route().handler(RootHandler.create());

        router.route("/").handler(ctx -> {
            ctx.response().end("home");
        });

        router.route("/test").handler(ctx -> {
            Object res = ctx.session().get("test");
            ctx.response().end(res == null ? "null" : res.toString());
        });

        server.requestHandler(router::accept).listen(8090, "localhost", res -> {
            if (res.succeeded())
                System.out.println("success");
        });
    }

    public static void main(String [] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(SessoinTest1.class.getName(), res -> System.out.println(res.result()));
    }

}
