package br.com.burnhop.api.controller;

import br.com.burnhop.model.Posts;
import br.com.burnhop.model.Users;
import br.com.burnhop.repository.PostsRepository;
import br.com.burnhop.repository.UsersRepository;

import java.util.ArrayList;
import java.util.Optional;

public class PostsController {

    PostsRepository posts_repository;
    UsersRepository users_repository;

    public PostsController(PostsRepository postsRepository, UsersRepository usersRepository) {
        this.posts_repository = postsRepository;
        this.users_repository = usersRepository;
    }

    public Posts createPost(Posts post) {
        Optional<Users> possibleUser = users_repository.findById(post.getUsers().getId());
        if(possibleUser.isPresent()) {
            posts_repository.save(post);

            Optional<Posts> createdPost = posts_repository.findById(post.getId());

            return createdPost.orElseThrow(IllegalStateException::new);
        }
        return null;
    }

    public ArrayList<Posts> getAllPosts() {
        ArrayList<Posts> posts = new ArrayList<>();

        for (Posts post : posts_repository.findAll()) {
            posts.add(post);
        }

        return posts;
    }

}
