package br.com.burnhop.api.resource;

import br.com.burnhop.api.controller.PostsController;
import br.com.burnhop.model.Dto.PostDto;
import br.com.burnhop.model.Dto.CreatedPostDto;
import br.com.burnhop.model.Posts;
import br.com.burnhop.repository.ContentRepository;
import br.com.burnhop.repository.UsersRepository;
import br.com.burnhop.repository.PostsRepository;
import br.com.burnhop.utils.TokenController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController()
@CrossOrigin("*")
@RequestMapping("/posts")
public class PostsResource {

    PostsController postController;
    UsersRepository usersRepository;
    TokenController tokenController;

    public PostsResource(PostsRepository post_repository, UsersRepository users_repository, ContentRepository content_repository) {
        postController = new PostsController(post_repository, users_repository, content_repository);
        usersRepository = users_repository;
        tokenController = new TokenController();
    }

    @PostMapping()
    @ApiOperation(value = "Criar um novo post", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Post criado com sucesso"),
            @ApiResponse(code = 401, message = "Token inválido"),
            @ApiResponse(code = 500, message = "Ocorreu um erro para processar a requisição")
    })
    public ResponseEntity<PostDto> createPost(
            @RequestBody CreatedPostDto newPost,
            @RequestHeader("Authorization") String token) {

        try {
            boolean validated = tokenController.validToken(token);

            if (validated) {

                PostDto post = postController.createPost(newPost.toPost(usersRepository));
                if (post == null) {
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
                return new ResponseEntity<>(post, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-all")
    @ApiOperation(value = "Todos os Posts", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Posts retornados com sucesso"),
            @ApiResponse(code = 204, message = "Não existe nenhum post salvo"),
            @ApiResponse(code = 401, message = "Token inválido"),
            @ApiResponse(code = 500, message = "Ocorreu um erro para processar a requisição")
    })
    public ResponseEntity<ArrayList<PostDto>> getAllPosts(
            @RequestHeader("Authorization") String token) {

        try {
            boolean validated = tokenController.validToken(token);

            if (validated) {
                ArrayList<PostDto> todos_posts = postController.getAllPosts();

                if (todos_posts.isEmpty())
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);

                return new ResponseEntity<>(todos_posts, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        } catch (IllegalAccessError e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}