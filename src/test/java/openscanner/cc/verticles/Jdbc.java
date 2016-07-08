package openscanner.cc.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLConnection;

/**
 * Created : sunc
 * Date : 16-6-20
 * Description :
 */
public class Jdbc {

    public static void handler(int id , Handler<String> resultHandler){

        Vertx vertx = Vertx.vertx();

        JsonObject json = new JsonObject();
        json.put("url","jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8");
        json.put("driver_class","com.mysql.jdbc.Driver");
        json.put("user","openscanner");
        json.put("password","wangwa");
        final JDBCClient client = JDBCClient.createNonShared(vertx,json);

//        vertx.eventBus().consumer("database", message->
//            client.getConnection(handler->{
//                if(handler.succeeded()){
//                    SQLConnection conn = handler.result();
//                    conn.query("select * from test where id = 1", res -> {
//                        JsonObject j = res.result().getRows().get(0);
//                        message.reply(j.toString());
//                    });
//                }
//            })
//        );

        client.getConnection(handler -> {
            if (handler.succeeded()) {
                SQLConnection conn = handler.result();
                conn.query("select * from test where id = " + id, res -> {
                    JsonObject j = res.result().getRows().get(0);
                    resultHandler.handle(j.toString());
                });
            } else {
                resultHandler.handle(handler.cause().getMessage());
            }
        });

    }
}
