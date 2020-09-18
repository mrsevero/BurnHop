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
        Login login = getLoginByEmail(email);
        if(login == null){
            Login login1 = new Login(email, hashPassword(pwd));
            login_repository.save(login1);
            Date data_nasc = Date.valueOf(date);
            Timestamp time = new Timestamp(System.currentTimeMillis());
            Users user = new Users(name, username, data_nasc, time);
            user.setLogin(login1);
            user_repository.save(user);

            return user;
        }
        return null;
    }

    public boolean authenticateUser(String email, String password) throws NoSuchAlgorithmException {

        Login login = getLoginByEmail(email);
        if(login == null){
            return false;
        }
        String hashPassword = hashPassword(password);

        return MessageDigest.isEqual(hashPassword.getBytes(), login.getPassword().getBytes());
    }

    public Users getUserByEmail(String email) {
        Users user = user_repository.findByEmail(email);
        return user;
    }

    public Login getLoginByEmail(String email) {
        Login login = login_repository.findByEmail(email);
        return login;
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {

        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(password.getBytes());
        byte[] hash = crypt.digest();

        return Base64.getEncoder().encodeToString(hash);
    }
}
