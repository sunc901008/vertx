package openscanner.cc.Verticles;

import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Locale;
import io.vertx.ext.web.Router;
import openscanner.cc.handler.APIPermissionHandler;
import openscanner.cc.handler.APIRootHandler;

import java.util.List;

public class Https {

    public static void main(String [] args) {

        // Properties properties = new Properties();
        // properties.load(new FileInputStream("conf/https.properties"));
        //
        // String server_keystore_path = properties.getProperty("server_keystore_path");
        // String server_keystore_password = properties.getProperty("server_keystore_password");
        // int server_keystore_port =
        // Integer.parseInt(properties.getProperty("server_keystore_port"));
        // JksOptions jks = new
        // JksOptions().setPath(server_keystore_path).setPassword(server_keystore_password);
        // HttpServerOptions options = new
        // HttpServerOptions().setSsl(true).setKeyStoreOptions(jks).setPort(server_keystore_port);
        Vertx vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);

        router.route().handler(APIRootHandler.create());

        router.route("/test").handler(APIPermissionHandler.create());

        router.route("/").handler(ctx -> {
            ctx.response().setChunked(true);
            MultiMap map = ctx.request().headers();
            for(String key : map.names())
                ctx.response().write(key + ":" + map.get(key) + "\n");
            ctx.response().end();

            System.out.println(ctx.request().getHeader("Accept-Language"));
            List<Locale> langList = ctx.acceptableLocales();
            if(langList.size() > 0){
                String language = langList.get(0).toString();
                System.out.println(language);

            }

        });

        server.requestHandler(router::accept).listen(8090,"localhost",res->{
            if(res.succeeded())
                System.out.println("success");
        });
    }

//    public static void main(String[] args) {
//        Vertx vertx = Vertx.vertx();
//        vertx.deployVerticle(Https.class.getName());
//    }

}
