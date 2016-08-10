package openscanner.cc;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import openscanner.handlebars.HandlebarsTemplateEngine;

import java.util.Set;

/*
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class Server {

    // Convenience method so you can run it in your IDE
    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();
        Router router = Router.router(vertx);

        // Enable multipart form data parsing
        router.route().handler(BodyHandler.create().setUploadsDirectory("uploads"));

        router.route("/").handler(routingContext -> {
            HandlebarsTemplateEngine.create().render(routingContext, "templates/file", res -> {
                if (res.succeeded()) {
                    routingContext.response().end(res.result());
                }
            });
        });

        // handle the form
        router.post("/upload").handler(ctx -> {
//            ctx.response().putHeader("Content-Type", "text/plain");
//
//            ctx.response().setChunked(true);
//
//            for (FileUpload f : ctx.fileUploads()) {
//                System.out.println("f");
//                ctx.response().write("Filename: " + f.fileName());
//                ctx.response().write("\n");
//                ctx.response().write("Size: " + f.size());
//            }
//
//            ctx.response().end();

            Set<FileUpload> uploads = ctx.fileUploads();
            ctx.response().setChunked(true);
            System.out.println("****************" + uploads.size());
            if (!uploads.isEmpty()) {
                FileUpload upload = uploads.iterator().next();
                String contentType = upload.contentType();//文件类型
                String fileName = upload.fileName();//文件名
                String tmpFileName = upload.uploadedFileName();//临时文件名
                ctx.response().write("contentType: " + contentType + "\n");
                ctx.response().write("Filename: " + fileName + "\n");
                ctx.response().write("tmpFileName: " + tmpFileName + "\n");
            }
            ctx.response().end("over");

        });

        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }
}