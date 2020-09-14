package br.com.burnhop.api.resource;

import br.com.burnhop.api.controller.UserController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController()
public class UsersResource {

    UserController userController;

    public UsersResource(){
        userController = new UserController();
    }

    @PostMapping(value = "/users")
    public ResponseEntity<String> createUser() {
        return new ResponseEntity<>("Usuário criado\n", HttpStatus.OK);
    }

    @GetMapping("/users/{email}")
    public ResponseEntity<String> getUser(@PathVariable(value = "email") String email) {
        return new ResponseEntity<>("Usuário:" + email + "\n", HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<String> getAllUsers() {
        return new ResponseEntity<>("Todos os usuários\n", HttpStatus.OK);
    }
}
