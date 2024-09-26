package net.codeer.app;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinJte;
import net.codeer.app.todo.MemoryTodoDAO;
import net.codeer.app.todo.MongoTodoDAO;
import net.codeer.app.todo.TodoController;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger LOG = LoggerFactory.getLogger("App");
    public static void main(String[] args) {
        LOG.info("Hello!");
        LOG.info("Args are: {}", Arrays.toString(args));

        var app = Javalin.create(config -> {
            config.showJavalinBanner = false;
            // Precompile templates so they will be available in container
            // replace with new JavalinJte() for local hot reload
            config.fileRenderer(new JavalinJte(TemplateEngine.createPrecompiled(ContentType.Html)));
            config.staticFiles.add("/static", Location.CLASSPATH);
        });

        if (args.length > 0 && args[0].equals("mongo")) {
            LOG.info("Using Mongo DAO!");
            MongoUtil.init(System.getenv("MONGO_CONNECTION_STRING"), "todos");
            new TodoController(new MongoTodoDAO(MongoUtil.getDatabase())).registerRoutes(app);

            app.events(event -> {
                event.serverStopping(MongoUtil::close);
            });
        } else {
            LOG.info("Using Memory DAO!");

            new TodoController(new MemoryTodoDAO()).registerRoutes(app);
        }

        app.start(7070);
    }
}
