package org.example.handlers;

import org.example.controller.PostController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NotFound implements Handler {
    @Override
    public void handle(PostController controller, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
}
