package br.com.burnhop.api.resource;

import br.com.burnhop.api.controller.PostsController;
import br.com.burnhop.api.controller.UserController;
import br.com.burnhop.model.dto.CreatedPostDto;
import br.com.burnhop.model.dto.PostDto;
import br.com.burnhop.model.dto.UpdatedPostDto;
import br.com.burnhop.model.dto.UserDto;
import br.com.burnhop.repository.ContentRepository;
import br.com.burnhop.repository.LoginRepository;
import br.com.burnhop.repository.UsersRepository;
import br.com.burnhop.repository.PostsRepository;
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
    UserController userController;
    UsersRepository usersRepository;

    public PostsResource(PostsRepository post_repository,
                         UsersRepository users_repository,
                         LoginRepository login_repository,
                         ContentRepository content_repository){
        postController = new PostsController(post_repository, users_repository, content_repository);
        this.userController = new UserController(login_repository, users_repository, post_repository);
        this.usersRepository = users_repository;
    }

    @PostMapping()
    @ApiOperation(value = "Criar um novo post", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Post criado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu um erro para processar a requisição")
    })
    public ResponseEntity<PostDto> createPost(@RequestBody CreatedPostDto newPost) {

        try {
            PostDto post = postController.createPost(newPost.toPost(usersRepository));
            if (post == null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            return new ResponseEntity(post, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{id}")
    @ApiOperation(value = "Todos os Posts de um Usuário", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Posts retornados com sucesso"),
            @ApiResponse(code = 204, message = "Não existe nenhum post salvo"),
            @ApiResponse(code = 404, message = "Não existe nenhum usuário registrado com id informado"),
            @ApiResponse(code = 500, message = "Ocorreu um erro para processar a requisição")
    })
    public ResponseEntity<ArrayList<PostDto>> getPostsByUsers(
            @PathVariable(value = "id") int id){

        try {
            UserDto user = userController.getUserById(id);

            if(user == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            ArrayList<PostDto> posts = postController.getAllPostsByUser(user);

            if(posts.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(posts, HttpStatus.OK);

        } catch (IllegalAccessError e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-all")
    @ApiOperation(value = "Todos os Posts", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Posts retornados com sucesso"),
            @ApiResponse(code = 204, message = "Não existe nenhum post salvo"),
            @ApiResponse(code = 500, message = "Ocorreu um erro para processar a requisição")
    })
    public ResponseEntity<ArrayList<PostDto>> getAllPosts(){

        try {
            ArrayList<PostDto> todos_posts = postController.getAllPosts();

            if(todos_posts.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(todos_posts, HttpStatus.OK);

        } catch (IllegalAccessError e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    @ApiOperation(value = "Retorna Post atualizado")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Post atualizado"),
            @ApiResponse(code = 400, message = "Conteúdo não pode ser nulo"),
            @ApiResponse(code = 404, message = "Nenhum post foi encontrado"),
            @ApiResponse(code = 500, message = "Ocorreu um erro para processar a requisição")
    })
    public ResponseEntity<PostDto> updatePost(
            @RequestBody UpdatedPostDto content,
            @PathVariable(value = "id") int id) {

        try {
            if(content.getContent().isBlank())
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            PostDto post = postController.getPostById(id);

            if(post == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            PostDto updatedPost = postController.updatePost(id, post, content);
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete post informado")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Post deletado com sucesso"),
            @ApiResponse(code = 404, message = "Nenhum post foi encontrado"),
            @ApiResponse(code = 500, message = "Ocorreu um erro para processar a requisição")
    })
    public ResponseEntity<String> deleteUser(
            @PathVariable (value = "id") int id) {

        try {
            PostDto post = postController.getPostById(id);

            if(post == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            boolean deleted = postController.deletePost(id);

            if(deleted)
                return new ResponseEntity<>("Post deletado com sucesso", HttpStatus.OK);

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}