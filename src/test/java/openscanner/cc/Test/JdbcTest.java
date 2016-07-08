package openscanner.cc.Test;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.jdbc.JDBCClient;
import openscanner.cc.verticles.Jdbc;
import openscanner.cc.verticles.Redis;

/**
 * Created : sunc
 * Date : 16-6-20
 * Description :
 */
public class JdbcTest {
    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();
        EventBus eb = vertx.eventBus();

        Future<String> a = Future.future();

        Future<String> b = Future.future();

        Future<String> c = Future.future();

        CompositeFuture.all(a, b, c).setHandler(ar -> {
            if (ar.succeeded()) {
                CompositeFuture fu = ar.result();
                System.out.println(fu.size());
            } else {
                ar.cause().printStackTrace();
            }
        });

        Jdbc.handler(1, h -> {
            a.complete(h);
        });

        Jdbc.handler(2, h -> {
            b.complete(h);
        });

        Redis.start(res -> {
            c.complete(res);
        });

    }
}
