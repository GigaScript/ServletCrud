package org.example.handlers;

import org.example.controller.PostController;
import org.example.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReceivePost implements Handler {
    @Override
    public void handle(PostController controller, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final var path = req.getRequestURI();
        try {
            final long id = Long.parseLong(path.substring(path.lastIndexOf("/")+1));
            controller.getById(id, resp);
        } catch (NumberFormatException exception) {
            throw new NotFoundException("Запрошен не корректный ID записи");
        }
    }
}
