package br.com.burnhop.api.controller;

import br.com.burnhop.model.Dto.UserDto;
import br.com.burnhop.model.Login;
import br.com.burnhop.model.Posts;
import br.com.burnhop.model.Users;
import br.com.burnhop.repository.LoginRepository;
import br.com.burnhop.repository.PostsRepository;
import br.com.burnhop.repository.UsersRepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PostsController {

    PostsRepository posts_repository;

    public PostsController(PostsRepository postsRepository) {
        this.posts_repository = postsRepository;
    }

    public void createPost(Posts post) {

    }

    public void getAllPosts() {

    }

    public void getPostByEmail() {
        
    }
}
