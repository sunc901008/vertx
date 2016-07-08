package openscanner.cc.Test;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTOptions;
import io.vertx.ext.web.Router;

/**
 * Created : sunc
 * Date : 16-6-22
 * Description :
 */
public class Http {
    public static void main(String [] args){
//        HttpClientOptions options = new HttpClientOptions().setSsl(true).setTrustAll(true);
//        HttpClient client = Vertx.vertx().createHttpClient(options);
//        String uccallback = "https://192.168.0.244:8089/api/logout";
////        try {
////            URL url = new URL(uccallback);
////            String host = url.getHost();
////            String uri = url.getPath();
////            System.out.println(host);
////            System.out.println(uri);
////            client.get(host, uri + "?type=login", h -> {}).exceptionHandler(Throwable::printStackTrace).end();
////        } catch (Exception e) {
////
////        }
//String jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjb25mdXNlIjoiWlRVeU5qVTJZVGxqTVRBd05ERTNZV0l3TVRReFptVmtNRE5rWVdZNU1Eaz0iLCJpYXQiOjE0NjY2OTE2MzV9.xIMj8D4R_jlLderLgTuqNRIlLSvS0r3syXP860duiM8=";
//        HttpRequest request = HttpRequest.get(uccallback).header("token",jwt);
//        // Accept all certificates
//        request.trustAllCerts();
//        // Accept all hostnames
//        request.trustAllHosts();
//        if (request.code() != HttpURLConnection.HTTP_OK) {
//
//        }
//        System.out.println(request.code());
//        System.out.println(request.body());
//
//        JsonObject json = new JsonObject();
//        String abc = json.getString("action");
//        System.out.println(Commons.isEmpty(abc));
        Vertx vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);

        router.route("/").handler(ctx -> {
             JsonObject authConfig = new JsonObject().put("keyStore",
                    new JsonObject().put("type", "jceks")
                            .put("path", "/opt/testJks/keystore.jceks")
                            .put("password", "secret"));
            JWTAuth authProvider = JWTAuth.create(vertx, authConfig);
            String token = authProvider.generateToken(new JsonObject().put("confuse", "test"), new JWTOptions());
            ctx.response().end(token);
        });

        router.route().failureHandler(ctx -> {
           ctx.failure().printStackTrace();
        });

        server.requestHandler(router::accept).listen(8090,"localhost",res->{
            if(res.succeeded())
                System.out.println("success");
        });

    }

}