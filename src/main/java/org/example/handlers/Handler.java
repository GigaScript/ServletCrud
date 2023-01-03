package org.example.handlers;

import org.example.controller.PostController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@FunctionalInterface
public interface Handler {
    void handle(PostController controller, HttpServletRequest req, HttpServletResponse resp) throws IOException;
}
