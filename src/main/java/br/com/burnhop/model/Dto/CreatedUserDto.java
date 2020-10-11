package br.com.burnhop.model.Dto;

import br.com.burnhop.model.Users;

import java.sql.Date;
import java.sql.Timestamp;

public class UserDto {

    private String name;

    private String username;

    private String data_nasc;

    private LoginDto login;

    public UserDto() {

    }

    public Users toUser() {
        Users user = new Users();
        user.setName(this.name);
        user.setUsername(this.username);
        user.setData_nasc(Date.valueOf(this.data_nasc));
        user.setCreated_on(new Timestamp(System.currentTimeMillis()));
        user.setLogin(this.login.toLogin());

        return user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getData_nasc() {
        return data_nasc;
    }

    public void setData_nasc(String data_nasc) {
        this.data_nasc = data_nasc;
    }

    public LoginDto getLogin() {
        return login;
    }

    public void setLogin(LoginDto login) {
        this.login = login;
    }

}
