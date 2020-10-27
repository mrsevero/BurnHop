package br.com.burnhop.api.controller;

import br.com.burnhop.model.Dto.CreatedUserDto;
import br.com.burnhop.model.Dto.UserDto;
import br.com.burnhop.model.Login;
import br.com.burnhop.model.Users;
import br.com.burnhop.repository.LoginRepository;
import br.com.burnhop.repository.UsersRepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class UserController {

    LoginRepository login_repository;
    UsersRepository user_repository;

    public UserController(LoginRepository loginRepository, UsersRepository usersRepository) {
        this.login_repository = loginRepository;
        this.user_repository = usersRepository;
    }

    public UserDto createUser(Users newUser) throws NoSuchAlgorithmException {
        Login login = getLoginByEmail(newUser.getLogin().getEmail());
        if(login == null){
            newUser.getLogin().setPassword(hashPassword(newUser.getLogin().getPassword()));
            login_repository.save(newUser.getLogin());
            user_repository.save(newUser);

            UserDto user = getUserByEmail(newUser.getLogin().getEmail());

            return user;
        }
        return null;
    }

    public boolean authenticateUser(String email, String password) throws NoSuchAlgorithmException {

        Login login = getLoginByEmail(email);
        if(login == null){
            throw new IllegalAccessError("E-mail não cadastrado");
        }
        String hashPassword = hashPassword(password);

        return MessageDigest.isEqual(hashPassword.getBytes(), login.getPassword().getBytes());
    }

    public UserDto getUserByEmail(String email) {
        return new UserDto(user_repository.findByEmail(email));
    }

    public Login getLoginByEmail(String email) {
        return login_repository.findByEmail(email);
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {

        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(password.getBytes());
        byte[] hash = crypt.digest();

        return Base64.getEncoder().encodeToString(hash);
    }
}
