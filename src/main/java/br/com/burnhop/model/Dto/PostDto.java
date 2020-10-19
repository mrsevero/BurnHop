package br.com.burnhop.model.Dto;

import br.com.burnhop.model.Posts;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Date;

public class PostDto {

    @ApiModelProperty(value = "Uma String que representa o apelido do usuário", example = "Teste_Exemplo_123")
    private String user_email;

    @ApiModelProperty(value = "Uma String que representa data de nascimento do usuário", example = "2020-10-18")
    private String texto;

    public PostDto() {

    }

    public PostDto(String email, String texto) {
        this.user_email = email;
        this.texto = texto;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
