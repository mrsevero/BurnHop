package br.com.burnhop.api.resource;

import br.com.burnhop.model.Users;
import br.com.burnhop.model.Login;
import java.sql.Timestamp;
import java.sql.Date;
import br.com.burnhop.repository.UsersRepository;
import br.com.burnhop.repository.LoginRepository;
import br.com.burnhop.api.controller.UserController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.websocket.server.PathParam;

@RestController()
public class UsersResource {

    UserController userController;
    private LoginRepository login_repository;
    private UsersRepository user_repository;

    public UsersResource(LoginRepository login_repository, UsersRepository user_repository){
        userController = new UserController();
        this.login_repository = login_repository;
        this.user_repository = user_repository;
    }

    @PostMapping(value = "/users")
    public ResponseEntity<String> createUser(@RequestParam("name") String name, @RequestParam("username") String username,
     @RequestParam("data_nasc") String date, @RequestParam("email") String email, @RequestParam("pwd") String pwd) {
        Login login1 = new Login(email, pwd);
        login_repository.save(login1);

        /* TODO: Criar método para criar usuário.*/
        Date data_nasc = Date.valueOf(date);
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Users user = new Users(name, username, data_nasc, time);
        user.setLogin(login1);
        user_repository.save(user);
        
        return new ResponseEntity<>("\nUsuário criado!\n Nome: "+user.getName()+"\n Username: "+user.getUsername()+"\n", HttpStatus.OK);
    }

    @GetMapping("/users/{email}")
    public ResponseEntity<String> getUser(@PathVariable(value = "email") String email) {
        // TODO: Criar método para devolver um usuário baseado em seu email.
        return new ResponseEntity<>("Usuário:" + email + "\n", HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<String> getAllUsers() {
        // TODO: Criar método para devolver todos os usuários. Utilizar findAll do CrudRepository
        return new ResponseEntity<>("Todos os usuários\n", HttpStatus.OK);
    }
}
