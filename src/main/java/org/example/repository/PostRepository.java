package org.example.repository;

import org.example.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    public List<Post> all();
    public Optional<Post> getById(long id);
    public Post save(Post post);
    public void removeById(long id);
}
