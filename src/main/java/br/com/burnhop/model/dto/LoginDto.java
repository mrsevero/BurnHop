package br.com.burnhop.model.dto;

import br.com.burnhop.model.Login;
import io.swagger.annotations.ApiModelProperty;

public class LoginDto {

    @ApiModelProperty(value = "Um inteiro que representa o identificador do usuário", example = "11")
    private int id;

    @ApiModelProperty(value = "Uma String que representa o e-mail do usuário", example = "exemplo@teste.com")
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
