package openscanner.cc.Verticles;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;

public class JdbcController {

    private static final Logger logger = LoggerFactory.getLogger(JdbcController.class);
    private SQLConnection conn;
    private JDBCClient client;

    private static JsonObject json;
    static {
        logger.info("load config file conf/jdbc.properties");
        json = new Property().ReadPropertiesToJsonObject("conf/jdbc.properties").put("provider_class", DruidDataSourceProvider.class.getName());
    }

    public JdbcController(Vertx vertx) {
        this.client = JDBCClient.createNonShared(vertx, json);
    }

//    public JdbcController(Vertx vertx) {
//        logger.info("load config file conf/jdbc.properties");
//        JsonObject json = new Property().ReadPropertiesToJsonObject("conf/jdbc.properties");
//        json.put("provider_class", DruidDataSourceProvider.class.getName());
//        this.client = JDBCClient.createNonShared(vertx, json);
//    }

    public void excute(HandlerUC<Boolean, Object> handler) {
        client.getConnection(connection -> {
            if (connection.failed()) {
                logger.error("JDBC connect failed! Reason : {0}", connection.cause().getMessage());
                handler.handle(false, "JDBC connect failed!");
                return;
            }
            conn = connection.result();
            String sql = "select day from inttable where day is not null";
            conn.query(sql, res -> {
                if (res.succeeded()) {
                    handler.handle(true, new JsonArray(res.result().getRows()));
                } else {
                    handler.handle(false, res.cause().getMessage());
                }
            });
        });
    }

    public void insert(int value, HandlerUC<Boolean, Object> handler) {
        client.getConnection(connection -> {
            if (connection.failed()) {
                logger.error("JDBC connect failed! Reason : {0}", connection.cause().getMessage());
                handler.handle(false, "JDBC connect failed!");
                return;
            }
            conn = connection.result();
            String sql = "insert into inttable(day) values (now())";
            if(value > 0){
                sql = "insert into inttable(day) values ('2016-07-15 16:13:17')";
            }
            conn.update(sql, res -> {
                if (res.succeeded()) {
                    handler.handle(true, res.result().getUpdated());
                } else {
                    handler.handle(false, res.cause().getMessage());
                }
            });
        });
    }
}
