package br.com.burnhop.model.dto;

import br.com.burnhop.model.Login;
import io.swagger.annotations.ApiModelProperty;

public class CreatedLoginDto {

    @ApiModelProperty(value = "Uma String que representa o e-mail do usuário", example = "exemplo@teste.com")
    private String email;

    @ApiModelProperty(value = "Uma String que representa a senha do usuário", example = "senha123")
    private String password;

    public CreatedLoginDto() {

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
