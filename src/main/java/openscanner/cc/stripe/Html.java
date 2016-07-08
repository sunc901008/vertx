package openscanner.cc.stripe;

import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * Created : sunc
 * Date : 16-7-8
 * Description :
 */
public class Html {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        router.route("/").handler(ctx -> ctx.response().sendFile("templates/pay.html"));

        router.route("/self").handler(ctx -> ctx.response().sendFile("templates/paySelf.html"));

        router.route("/pay").handler(ctx -> {
            ctx.response().setChunked(true);
            MultiMap params = ctx.request().params();
            for(String key : params.names()){
                ctx.response().write(key + ":" + params.get(key) + "\n");
            }
            ctx.response().write("\n");
            ctx.response().write("\n");
            ctx.response().write("***************");
            ctx.response().write("\n");
            ctx.response().write("\n");
            MultiMap headers = ctx.request().headers();
            for(String key : headers.names()){
                ctx.response().write(key + ":" + headers.get(key) + "\n");
            }
            ctx.response().end();
        });

        vertx.createHttpServer().requestHandler(router::accept).listen(8000, res -> {
            if (res.succeeded())
                System.out.println("start");
            else
                res.cause().printStackTrace();
        });

    }
}
