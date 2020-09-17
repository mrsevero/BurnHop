package br.com.burnhop.api.controller;

import br.com.burnhop.model.Login;
import br.com.burnhop.model.Users;
import br.com.burnhop.repository.LoginRepository;
import br.com.burnhop.repository.UsersRepository;
import org.apache.catalina.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Base64;

public class UserController {

    LoginRepository login_repository;
    UsersRepository user_repository;

    public UserController(LoginRepository loginRepository, UsersRepository usersRepository) {
        this.login_repository = loginRepository;
        this.user_repository = usersRepository;
    }

    public Users createUser(String name, String username, String email, String pwd, String date) throws NoSuchAlgorithmException {
        Login login1 = new Login(email, hashPassword(pwd));
        login_repository.save(login1);

        /* TODO: Criar método para criar usuário.*/
        Date data_nasc = Date.valueOf(date);
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Users user = new Users(name, username, data_nasc, time);
        user.setLogin(login1);
        user_repository.save(user);

        return user;
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {

        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(password.getBytes());
        byte[] hash = crypt.digest();

        return Base64.getEncoder().encodeToString(hash);
    }
}
