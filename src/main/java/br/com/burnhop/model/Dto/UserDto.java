package br.com.burnhop.model.Dto;

import br.com.burnhop.model.Users;

import java.sql.Date;
import java.sql.Timestamp;

public class UserDto {

    private int id;

    private String name;

    private String username;

    private Date data_nasc;

    private LoginDto login;

    public UserDto() {

    }

    public UserDto(Users user) {
        this.id = user.getId();
        this.name = user.getName();
        this.username = user.getUsername();
        this.data_nasc = user.getData_nasc();
        this.login = new LoginDto(user.getLogin());
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

    public Date getData_nasc() {
        return data_nasc;
    }

    public void setData_nasc(Date data_nasc) {
        this.data_nasc = data_nasc;
    }

    public LoginDto getLogin() {
        return login;
    }

    public void setLogin(LoginDto login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
