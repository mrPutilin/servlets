package ru.netology.service;

import ru.netology.model.Post;
import ru.netology.repository.PostRepository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class PostService {
  private final PostRepository repository;

  public PostService(PostRepository repository) {
    this.repository = repository;
  }

  public ConcurrentHashMap<Long, Post> all() {
    return repository.all();
  }

  public Optional<Post> getById(long id) {
    return repository.getById(id);
//            orElseThrow(NotFoundException::new);
  }

  public Post save(Post post) {
    return repository.save(post);
  }

  public void removeById(long id) {
    repository.removeById(id);
  }
}

