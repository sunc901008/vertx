package openscanner.cc.utils;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.jwt.JWTAuth;

public class Security {
	
	public static JWTAuth createJWTAuth(Vertx vertx){
		JsonObject authConfig = new JsonObject().put("keyStore",
				new JsonObject().put("type", "jceks")
						.put("path", "/opt/testJks/keystore.jceks")
						.put("password", "wangwa"));
        return JWTAuth.create(vertx, authConfig);
	}
	
}
