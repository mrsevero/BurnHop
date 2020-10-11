package br.com.burnhop.model.Dto;

import br.com.burnhop.model.Login;

public class LoginDto {

    private int id;

    private String email;

    public LoginDto() {

    }

    public LoginDto(Login login) {
        this.id = login.getId();
        this.email = login.getEmail();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
