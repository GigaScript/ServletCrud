package org.example.controller;

import org.example.handlers.*;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Controller
public class HandlerController {
    private final Map<String, Map<String, Handler>> handlers = new ConcurrentHashMap<>();

    public HandlerController() {
        addHandler("GET", "/api/posts", new ReceiveAllPost());
        addHandler("GET", "/api/posts/", new ReceivePost());
        addHandler("POST", "/api/posts", new SavePost());
        addHandler("DELETE", "/api/posts/", new DeletePost());
    }

    public void addHandler(String requestMethod, String requestPath, Handler handler) {
        Map<String, Handler> handlerMap = new ConcurrentHashMap<>();
        if (handlers.containsKey(requestMethod)) {
            handlerMap = handlers.get(requestMethod);
        }
        handlerMap.put(requestPath, handler);
        handlers.put(requestMethod, handlerMap);
    }

    public Handler getHandler(HttpServletRequest req) {
        String path = req.getRequestURI();
        String method = req.getMethod();
        if (path.matches("/api/posts/\\d+")) {
            path = path.substring(0, path.lastIndexOf("/")+1);
        }
        System.out.println(path);
        Handler handler;
        if (handlers.containsKey(method)) {
            if (handlers.get(method).containsKey(path)) {
                return handlers.get(method).get(path);
            }
        }
        return new NotFound();
    }

    public Handler getServerErrorHandler(HttpServletResponse resp) {
        return new InternalServerError();
    }
}
