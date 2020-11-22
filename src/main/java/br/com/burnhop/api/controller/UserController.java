package br.com.burnhop.api.controller;

import br.com.burnhop.model.dto.UpdatedUserDto;
import br.com.burnhop.model.dto.UserDto;
import br.com.burnhop.model.Login;
import br.com.burnhop.model.Posts;
import br.com.burnhop.model.Users;
import br.com.burnhop.repository.LoginRepository;
import br.com.burnhop.repository.PostsRepository;
import br.com.burnhop.repository.UsersRepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Optional;

public class UserController {

    LoginRepository login_repository;
    UsersRepository user_repository;
    PostsRepository posts_repository;

    public UserController(LoginRepository loginRepository, UsersRepository usersRepository, PostsRepository postsRepository) {
        this.login_repository = loginRepository;
        this.user_repository = usersRepository;
        this.posts_repository = postsRepository;
    }

    public UserDto createUser(Users newUser) throws NoSuchAlgorithmException {
        Login login = getLoginByEmail(newUser.getLogin().getEmail());
        if(login == null){
            newUser.getLogin().setPassword(hashPassword(newUser.getLogin().getPassword()));
            newUser.setImage_path("");
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

    public UserDto getUserById(int id) {
        Optional<Users> user = user_repository.findById((Integer) id);

        return user.map(UserDto::new).orElse(null);
    }

    public ArrayList<UserDto> getAllUsers() {

        ArrayList<UserDto> users = new ArrayList<>();

        for (Users user : user_repository.findAll()) {
            users.add(new UserDto(user));
        }

        return users;
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

    public UserDto updateUser(int id, UserDto user, UpdatedUserDto newUser) {
        Users possibleUser = user_repository.findByEmail(newUser.getEmail());

        if(possibleUser != null)
            return null;

        Date date = newUser.getData_nasc().isEmpty() ?
                user.getData_nasc() :
                Date.valueOf(newUser.getData_nasc());

        String name = newUser.getName().isEmpty() ?
                user.getName() :
                newUser.getName();

        String username = newUser.getData_nasc().isEmpty() ?
                user.getUsername() :
                newUser.getUsername();

        String email = newUser.getData_nasc().isEmpty() ?
                user.getLogin().getEmail() :
                newUser.getEmail();

        Users userToUpdate = user_repository.findById((Integer) id).get();

        userToUpdate.setData_nasc(date);
        userToUpdate.setName(name);
        userToUpdate.setUsername(username);
        userToUpdate.getLogin().setEmail(email);

        Users updatedUser = user_repository.save(userToUpdate);

        return new UserDto(updatedUser);
    }

    public UserDto updateImagePath(int id, String image) {
        Optional<Users> userToUpdate = user_repository.findById((Integer) id);

        if(userToUpdate.isPresent()) {

            userToUpdate.get().setImage_path(image);
            Users updatedUser = user_repository.save(userToUpdate.get());

            return new UserDto(updatedUser);
        }

        return null;
    }

    public boolean deleteUser(int id) {
        Optional <Users> user = user_repository.findById((Integer) id);

        if(user.isPresent()) {
            Users userToDelete = user.get();
            int loginId = userToDelete.getLogin().getId();
            ArrayList<Posts> posts = new ArrayList<>();

            for (Posts post : posts_repository.findAll()) {
                posts.add(post);
            }

            for (Posts post : posts) {
                if (post.getUsers().getId() == id){
                    posts_repository.deleteById(post.getId());
                }
            }
            user_repository.deleteById((Integer) userToDelete.getId());
            login_repository.deleteById((Integer) loginId);
            return true;
        }

        return false;

    }
}
