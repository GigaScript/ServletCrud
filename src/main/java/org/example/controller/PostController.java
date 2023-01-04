package org.example.controller;

import com.google.gson.Gson;
import org.example.model.Post;
import org.example.service.PostService;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
@Controller
public class PostController {
    public static final String APPLICATION_JSON = "application/json";
    private final Gson gson = new Gson();
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    public void all(HttpServletResponse response) throws IOException {
        final var data = service.all();
        sentJsonResponse(data, response);
    }

    public void getById(long id, HttpServletResponse response) throws IOException {
        sentJsonResponse(service.getById(id), response);
    }

    public void save(Reader body, HttpServletResponse response) throws IOException {
        final var data = gson.fromJson(body, Post.class);
        sentJsonResponse(service.save(data), response);

    }

    public void removeById(long id, HttpServletResponse response) throws IOException {
        service.removeById(id);
        sentJsonResponse("Запись с ID=" + id + " удалена", response);
    }

    private <T> void sentJsonResponse(T data, HttpServletResponse response) throws IOException {
        String jsonString = gson.toJson(data);
        response.setContentType(APPLICATION_JSON);
        response.getWriter().print(gson.toJson(data));
    }
}
