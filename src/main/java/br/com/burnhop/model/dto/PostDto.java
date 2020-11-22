package br.com.burnhop.model.dto;

import br.com.burnhop.model.Posts;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

public class PostDto {

    @ApiModelProperty(value = "Um inteiro que representa o identificador do post", example = "10")
    private int id;

    @ApiModelProperty(value = "Uma String que representa o apelido do usuario do post", example = "Exemplo")
    private String username;

    @ApiModelProperty(value = "Uma String que representa o caminho da imagem do usuario do post", example = "user-images/teste.jpg")
    private String image_path;

    @ApiModelProperty(value = "Uma String que representa o texto do post", example = "artigo de como fazer um passinho do hip-hop")
    private String texto;

    @ApiModelProperty(value = "Uma String que representa a data do post", example = "2020-10-19T03:36:16.865+00:00")
    private Timestamp dataPost;

    public PostDto() {

    }

    public PostDto(Posts post) {
        this.id = post.getId();
        this.username = post.getUsers().getUsername();
        this.image_path = post.getUsers().getImage_path();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return image_path;
    }

    public void setImagePath(String image_path) {
        this.image_path = image_path;
    }
}
