package br.com.burnhop.api.resource;

import br.com.burnhop.model.Dto.CreatedUserDto;
import br.com.burnhop.model.Dto.UserDto;
import br.com.burnhop.model.Users;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import br.com.burnhop.repository.UsersRepository;
import br.com.burnhop.repository.LoginRepository;
import br.com.burnhop.api.controller.UserController;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@CrossOrigin("*")
@RequestMapping("/users")
public class UsersResource {

    UserController userController;

    public UsersResource(LoginRepository login_repository, UsersRepository user_repository){
        userController = new UserController(login_repository, user_repository);
    }

    @PostMapping()
    @ApiOperation(value = "Criar um novo usuário", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuário cadastrado com sucesso"),
            @ApiResponse(code = 409, message = "Usuário com este e-mail já está cadastrado"),
            @ApiResponse(code = 500, message = "Ocorreu um erro para processar a requisição")
    })
    public ResponseEntity<UserDto> createUser(@RequestBody CreatedUserDto newUser) throws NoSuchAlgorithmException {

        try {
            UserDto user = userController.createUser(newUser.toUser());
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    @ApiOperation(value = "Autenticação", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuário autenticado com sucesso"),
            @ApiResponse(code = 401, message = "Usuário não autenticado"),
            @ApiResponse(code = 500, message = "Ocorreu um erro para processar a requisição")
    })
    public ResponseEntity<String> login(
            @RequestHeader(value = "email") String email,
            @RequestHeader(value = "password") String password) throws NoSuchAlgorithmException {

        try {
            boolean authenticate = userController.authenticateUser(email, password);

            if (authenticate)
                return new ResponseEntity<>("Autenticado\n", HttpStatus.OK);

            return new ResponseEntity<>("Não autenticado\n", HttpStatus.UNAUTHORIZED);

        } catch (IllegalAccessError e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/email/{email}")
    @ApiOperation(value = "Retorna usuário baseado no e-mail")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuário com e-mail"),
            @ApiResponse(code = 404, message = "Usuário com e-mail não encontrado"),
            @ApiResponse(code = 500, message = "Ocorreu um erro para processar a requisição")
    })
    public ResponseEntity<UserDto> getUserByEmail(
            @PathVariable(value = "email") String email) {

        try {
            UserDto user = userController.getUserByEmail(email);

            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/id/{id}")
    @ApiOperation(value = "Retorna usuário baseado no id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuário com id"),
            @ApiResponse(code = 404, message = "Usuário com id não encontrado"),
            @ApiResponse(code = 500, message = "Ocorreu um erro para processar a requisição")
    })
    public ResponseEntity<UserDto> getUserById(
            @PathVariable(value = "id") int id) {

        try {
            UserDto user = userController.getUserById(id);

            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-all")
    @ApiOperation(value = "Retorna todos os usuários")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de todos os usuários"),
            @ApiResponse(code = 404, message = "Nenhum usuário foi encontrado"),
            @ApiResponse(code = 500, message = "Ocorreu um erro para processar a requisição")
    })
    public ResponseEntity<ArrayList<UserDto>> getAllUsers() {

        try {
            ArrayList<UserDto> users = userController.getAllUsers();

            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
