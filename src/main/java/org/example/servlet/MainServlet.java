package org.example.servlet;

import org.example.Config;
import org.example.controller.HandlerController;
import org.example.controller.PostController;
import org.example.handlers.Handler;
import org.example.repository.InMemoryPostRepository;
import org.example.service.PostService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class MainServlet extends HttpServlet {
    private PostController postController;
    private HandlerController handlerController;

    @Override
    public void init() {
        final var context  = new AnnotationConfigApplicationContext(Config.class);
        postController = context.getBean(PostController.class);
        handlerController = context.getBean(HandlerController.class);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // если деплоились в root context, то достаточно этого
        try {
            Handler handler = handlerController.getHandler(req);
            handler.handle(postController, req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            Handler handler = handlerController.getServerErrorHandler(resp);
            handler.handle(postController, req, resp);

        }
    }
}

