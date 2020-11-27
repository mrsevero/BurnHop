package br.com.burnhop.model.dto;

import br.com.burnhop.model.Content;
import br.com.burnhop.model.Posts;
import br.com.burnhop.repository.UsersRepository;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

public class CreatedPostDto {

    @ApiModelProperty(value = "Uma String que representa o apelido do usuário", example = "exemplo@teste.com")
    private String user_email;

    @ApiModelProperty(value = "Uma String que representa data de nascimento do usuário", example = "2020-10-18")
    private String texto;

    public CreatedPostDto() {

    }

    public Posts toPost(UsersRepository usersRepository) {
        Posts post = new Posts();
        Content content = new Content();
        content.setText(texto);
        post.setUsers(usersRepository.findByEmail(this.user_email));
        post.setPosted_on(new Timestamp(System.currentTimeMillis()));
        post.setContent(content);

        return post;
    }

    public void setUserEmail(String user_email) {
        this.user_email = user_email;
    }

    public String getUserEmail(){
        return this.user_email;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTexto(){
        return this.texto;
    }
}
