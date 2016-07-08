package openscanner.cc.Verticles;

import io.vertx.core.Vertx;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import openscanner.handlebars.HandlebarsTemplateEngine;

import java.util.Set;

/**
 * Created : sunc
 * Date : 16-7-6
 * Description :
 */
public class Http {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        Router route = Router.router(vertx);

        route.route().handler(BodyHandler.create());


        route.route("/").handler(ctx -> {
            HandlebarsTemplateEngine.create().render(ctx, "templates/file", res -> {
                if (res.succeeded()) {
                    ctx.response().end(res.result());
                } else {
                    res.cause().printStackTrace();
                }
            });
        });

        route.route("/upload").handler(ctx -> {
            Set<FileUpload> uploads = ctx.fileUploads();
            FileUpload upload = uploads.iterator().next();
            String contentType = upload.contentType();//文件类型
            String fileName = upload.fileName();//文件名
            String tmpFileName = upload.uploadedFileName();//临时文件名
            long size = upload.size();//size

            System.out.println(contentType);
            System.out.println(fileName);
            System.out.println(tmpFileName);
            System.out.println(size);

            ctx.response().setChunked(true);
            ctx.response().write(contentType + "\n");
            ctx.response().write(fileName + "\n");
            ctx.response().write(tmpFileName + "\n");
            ctx.response().write(size + "\n");
            ctx.response().end();
        });

        vertx.createHttpServer().requestHandler(route::accept).listen(8085);


    }
}
