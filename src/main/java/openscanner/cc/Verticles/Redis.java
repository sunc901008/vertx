package openscanner.cc.Verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;


/**
 * @author sunc
 * @date 2016年3月21日
 * @description : redis 存储
 */
public class Redis extends AbstractVerticle{

	public void start(){
        RedisOptions config = new RedisOptions().setHost("127.0.0.1");
        RedisClient redis = RedisClient.create(vertx, config);
		vertx.eventBus().consumer("redis", message -> redis.get("test",res -> message.reply(res.result())));
	}

}
