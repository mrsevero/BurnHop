package br.com.burnhop.api.resource;

import br.com.burnhop.model.Users;

import java.security.NoSuchAlgorithmException;

import br.com.burnhop.repository.UsersRepository;
import br.com.burnhop.repository.LoginRepository;
import br.com.burnhop.api.controller.UserController;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<String> createUser(@RequestParam("name") String name, @RequestParam("username") String username,
     @RequestParam("data_nasc") String date, @RequestParam("email") String email, @RequestParam("pwd") String pwd) throws NoSuchAlgorithmException {

        Users user = userController.createUser(name, username, email, pwd, date);
        if (user == null) {
            return new ResponseEntity<>("Usuário já cadastrado\n", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(user+"\n", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestHeader(value = "email") String email,
            @RequestHeader(value = "password") String password) throws NoSuchAlgorithmException {

        try {
            boolean authenticate = userController.authenticateUser(email, password);

            if (authenticate)
                return new ResponseEntity<>("Autorizado\n", HttpStatus.OK);

            return new ResponseEntity<>("Não autorizado\n", HttpStatus.UNAUTHORIZED);

        } catch (IllegalAccessError e) {
            return new ResponseEntity<>("Email nao cadastrado\n", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<String> getUser(
            @PathVariable(value = "email") String email) {

        Users user = userController.getUserByEmail(email);

        if (user == null) {
            return new ResponseEntity<>("Usuário não cadastrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user + "\n", HttpStatus.OK);
    }
}
