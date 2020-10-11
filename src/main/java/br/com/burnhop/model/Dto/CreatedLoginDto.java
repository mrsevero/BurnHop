package br.com.burnhop.model.Dto;

import br.com.burnhop.model.Login;

public class LoginDto {

    private String email;

    private String password;

    public LoginDto() {

    }

    public Login toLogin() {
        Login login = new Login();
        login.setEmail(this.getEmail());
        login.setPassword(this.getPassword());

        return login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
