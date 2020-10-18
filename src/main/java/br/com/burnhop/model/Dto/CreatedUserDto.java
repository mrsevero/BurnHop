package br.com.burnhop.model.Dto;

import br.com.burnhop.model.Users;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import java.sql.Date;
import java.sql.Timestamp;

public class CreatedUserDto {

    @ApiModelProperty(value = "Uma String que representa o nome do usuário", example = "Nome Exemplo Teste")
    private String name;

    @ApiModelProperty(value = "Uma String que representa o apelido do usuário", example = "Teste_Exemplo_123")
    private String username;

    @ApiModelProperty(value = "Uma String que representa data de nascimento do usuário", example = "2020-10-18")
    private String data_nasc;

    private CreatedLoginDto login;

    public CreatedUserDto() {

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

    public CreatedLoginDto getLogin() {
        return login;
    }

    public void setLogin(CreatedLoginDto login) {
        this.login = login;
    }

}
