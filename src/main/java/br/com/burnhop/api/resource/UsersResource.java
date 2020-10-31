package br.com.burnhop.api.resource;

import br.com.burnhop.model.Dto.CreatedUserDto;
import br.com.burnhop.model.Dto.UserDto;
import br.com.burnhop.model.Users;

import java.security.NoSuchAlgorithmException;

import br.com.burnhop.repository.UsersRepository;
import br.com.burnhop.repository.LoginRepository;
import br.com.burnhop.api.controller.UserController;
import br.com.burnhop.utils.TokenController;
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
    TokenController tokenController;

    public UsersResource(LoginRepository login_repository, UsersRepository user_repository){
        userController = new UserController(login_repository, user_repository);
        tokenController = new TokenController();
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

            if (authenticate) {
                String token = tokenController.createToken(userController.getUserByEmail(email).getId());
                return new ResponseEntity<>("Bearer " + token, HttpStatus.OK);

            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        } catch (IllegalAccessError e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{email}")
    @ApiOperation(value = "Retorna usuário baseado no e-mail")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuário com e-mail"),
            @ApiResponse(code = 401, message = "Token inválido"),
            @ApiResponse(code = 404, message = "Usuário com e-mail não encontrado"),
            @ApiResponse(code = 500, message = "Ocorreu um erro para processar a requisição")
    })
    public ResponseEntity<UserDto> getUser(
            @PathVariable(value = "email") String email,
            @RequestHeader("Authorization") String token) {

        try {
            boolean validated = tokenController.validToken(token);

            if(validated) {
                UserDto user = userController.getUserByEmail(email);

                if (user == null) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(user, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
