package openscanner.cc.handlerImpl;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;
import openscanner.cc.handler.APIPermissionHandler;

import java.util.regex.Pattern;

public class APIPermissionHandlerImpl implements APIPermissionHandler {

    @Override
    public void handle(RoutingContext ctx) {
        Object remote_address = ctx.get("remote_address");
        if(remote_address == null){
            ctx.response().end("no login");
            return;
        }

        if(ctx.request().method() == HttpMethod.GET){
            ctx.response().end("get get");
            return;
        }

        ctx.request().bodyHandler(body -> {
            JsonObject json = body.toJsonObject();
            System.out.println(json);
            postHandler(ctx, json, new JsonObject());
        });
    }

    private static void postHandler(RoutingContext ctx, JsonObject json, JsonObject roles){

        for(String key : json.fieldNames()){
            if(isNumeric(json.getValue(key))){
                roles.put(key, (roles.getInteger(key, 0) + json.getInteger(key)));
            }else{
                ctx.response().end("params error");
                return;
            }
        }
        ctx.response().end("success");
    }

    public static boolean isNumeric(Object object){
        String str = object.toString();
        if("".equals(str)){
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
}
