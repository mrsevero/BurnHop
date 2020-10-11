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

    private Timestamp created_on;

    public UserDto() {

    }

    public UserDto(Users user) {
        this.id = user.getId();
        this.name = user.getName();
        this.username = user.getUsername();
        this.data_nasc = user.getData_nasc();
        this.created_on = user.getCreated_on();
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

    public Timestamp getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Timestamp created_on) {
        this.created_on = created_on;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
