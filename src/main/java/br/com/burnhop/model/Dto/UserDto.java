package br.com.burnhop.model.Dto;

import br.com.burnhop.model.Users;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Date;
import java.sql.Timestamp;

public class UserDto {

    @ApiModelProperty(value = "Um inteiro que representa o identificador do usuário", example = "10")
    private int id;

    @ApiModelProperty(value = "Uma String que representa o nome do usuário", example = "Nome Exemplo Teste")
    private String name;

    @ApiModelProperty(value = "Uma String que representa o apelido do usuário", example = "Teste_Exemplo_123")
    private String username;

    @ApiModelProperty(value = "Uma String que representa data de nascimento do usuário", example = "2020-10-18")
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
