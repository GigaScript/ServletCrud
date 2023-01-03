package org.example.servlet;

import org.example.controller.HandlerController;
import org.example.controller.PostController;
import org.example.handlers.Handler;
import org.example.repository.InMemoryPostRepository;
import org.example.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class MainServlet extends HttpServlet {
    private PostController postController;
    private HandlerController handlerController;

    @Override
    public void init() {
        final var repository = new InMemoryPostRepository();
        final var service = new PostService(repository);
        postController = new PostController(service);
        handlerController = new HandlerController();
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

