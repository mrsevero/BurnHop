package br.com.burnhop.api.resource;

import br.com.burnhop.model.Users;
import br.com.burnhop.model.Login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.Base64;

import br.com.burnhop.repository.UsersRepository;
import br.com.burnhop.repository.LoginRepository;
import br.com.burnhop.api.controller.UserController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.websocket.server.PathParam;

@RestController()
@RequestMapping("/users")
public class UsersResource {

    UserController userController;

    public UsersResource(LoginRepository login_repository, UsersRepository user_repository){
        userController = new UserController(login_repository, user_repository);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<String> createUser(@RequestParam("name") String name, @RequestParam("username") String username,
     @RequestParam("data_nasc") String date, @RequestParam("email") String email, @RequestParam("pwd") String pwd) throws NoSuchAlgorithmException {

        Users user = userController.createUser(name, username, email, pwd, date);
        return new ResponseEntity<>(user+"\n", HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(
            @RequestHeader(value = "email") String email,
            @RequestHeader(value = "password") String password) throws NoSuchAlgorithmException {

        boolean authenticate = userController.authenticateUser(email, password);

        if(authenticate)
            return new ResponseEntity<>("Autorizado", HttpStatus.OK);

        return new ResponseEntity<>("Não autorizado", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{email}")
    public ResponseEntity<String> getEmailLogin(
            @PathVariable(value = "email") String email) {

        Login login = userController.getUserByEmail(email);

        return new ResponseEntity<>("Usuário:" + login.getPassword() + "\n", HttpStatus.OK);
    }
}
