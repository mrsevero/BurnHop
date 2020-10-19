package br.com.burnhop.model.Dto;

import br.com.burnhop.model.Posts;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Date;

public class PostDto {

    @ApiModelProperty(value = "Uma String que representa o email do usuario do post", example = "exemplo@teste.com")
    private String user_email;

    @ApiModelProperty(value = "Uma String que representa o texto do post", example = "artigo de como fazer um passinho do hip-hop")
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
