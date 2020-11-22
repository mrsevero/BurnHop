package br.com.burnhop.api.controller;

import br.com.burnhop.model.Content;
import br.com.burnhop.model.dto.PostDto;
import br.com.burnhop.model.dto.UpdatedPostDto;
import br.com.burnhop.model.Posts;
import br.com.burnhop.model.Users;
import br.com.burnhop.repository.ContentRepository;
import br.com.burnhop.repository.PostsRepository;
import br.com.burnhop.repository.UsersRepository;

import java.util.ArrayList;
import java.util.Optional;

public class PostsController {

    PostsRepository posts_repository;
    ContentRepository content_repository;
    UsersRepository users_repository;

    public PostsController(PostsRepository postsRepository, UsersRepository usersRepository, ContentRepository contentRepository) {
        this.posts_repository = postsRepository;
        this.users_repository = usersRepository;
        this.content_repository = contentRepository;
    }

    public PostDto createPost(Posts post) {
        Optional<Users> possibleUser = users_repository.findById(post.getUsers().getId());
        if(possibleUser.isPresent()) {
            Content content = post.getContent();
            content_repository.save(content);
            posts_repository.save(post);

            Optional<Posts> createdPost = posts_repository.findById(post.getId());

            return new PostDto(createdPost.get());
        }
        return null;
    }

    public ArrayList<PostDto> getAllPosts() {
        ArrayList<PostDto> posts = new ArrayList<>();

        for (Posts post : posts_repository.findAll()) {
            posts.add(new PostDto(post));
        }

        return posts;
    }

    public PostDto getPostById(int id) {
        Optional<Posts> post = posts_repository.findById((Integer) id);

        return post.map(PostDto::new).orElse(null);
    }

    public PostDto updatePost(int id, PostDto post, UpdatedPostDto content) {
        Posts postToUpdate = posts_repository.findById((Integer) id).get();

        Content newContent = new Content();

        newContent.setId(postToUpdate.getContent().getId());
        newContent.setText(content.getContent());

        Content updatedContent = content_repository.save(newContent);

        postToUpdate.setContent(updatedContent);

        Posts updatedPost = posts_repository.save(postToUpdate);

        return new PostDto(updatedPost);
    }

    public boolean deletePost(int id) {
        Optional<Posts> post = posts_repository.findById((Integer) id);

        if(post.isPresent()) {
            Posts postToDelete = post.get();
            int contentId = postToDelete.getContent().getId();

            posts_repository.deleteById((Integer) postToDelete.getId());
            content_repository.deleteById((Integer) contentId);
            return true;
        }

        return false;

    }

}
