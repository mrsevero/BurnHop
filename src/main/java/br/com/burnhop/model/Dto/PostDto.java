package br.com.burnhop.model.Dto;

import br.com.burnhop.model.Posts;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Date;
import java.sql.Timestamp;

public class PostDto {

    @ApiModelProperty(value = "Uma String que representa o apelido do usuario do post", example = "Exemplo")
    private String username;

    @ApiModelProperty(value = "Uma String que representa o texto do post", example = "artigo de como fazer um passinho do hip-hop")
    private String texto;

    @ApiModelProperty(value = "Uma String que representa a data do post", example = "2020-10-19T03:36:16.865+00:00")
    private Timestamp dataPost;

    public PostDto() {

    }

    public PostDto(Posts post) {
        this.username = post.getUsers().getUsername();
        this.texto = post.getContent().getText();
        this.dataPost = post.getPosted_on();
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getDataPost() {
        return dataPost;
    }

    public void setDataPost(Timestamp dataPost) {
        this.dataPost = dataPost;
    }
}
