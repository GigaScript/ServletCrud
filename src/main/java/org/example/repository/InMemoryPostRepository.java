package org.example.repository;

import org.example.exception.NotFoundException;
import org.example.model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryPostRepository implements PostRepository {
    private final List<Post> posts = Collections.synchronizedList(new ArrayList<>());
    private AtomicLong counter = new AtomicLong();

    @Override
    public List<Post> all() {
        return posts;
    }

    @Override
    public Optional<Post> getById(long id) {
        return findPostById(id);
    }

    @Override
    public Post save(Post post) {

        if (post.getId() == 0) {
            post.setId(
                    counter.addAndGet(1)
            );
            posts.add(post);
            return post;
        }
        Optional<Post> originalPost = findPostById(post.getId());
        if (originalPost.isPresent()) {
            posts.set(posts.indexOf(originalPost.get()), post);
        }
        return post;
    }

    @Override
    public void removeById(long id) {
        Optional<Post> post = findPostById(id);
        if (post.isPresent()) {
            posts.remove(post.get());
        }
    }

    private Optional<Post> findPostById(long id) {
        return posts.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }

}
