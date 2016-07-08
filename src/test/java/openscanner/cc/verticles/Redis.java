package openscanner.cc.verticles;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;


/**
 * @author sunc
 * @date 2016年3月21日
 * @description : redis 存储
 */
public class Redis {

	public static void start(Handler<String> handler){
		Vertx vertx = Vertx.vertx();
        RedisOptions config = new RedisOptions().setHost("127.0.0.1");
        RedisClient redis = RedisClient.create(vertx, config);
		redis.get("test",res -> handler.handle(res.result()));
	}

}
