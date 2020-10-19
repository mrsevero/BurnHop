package br.com.burnhop.model.Dto;

import br.com.burnhop.model.Content;
import br.com.burnhop.model.Posts;
import br.com.burnhop.repository.UsersRepository;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.sql.Timestamp;

public class CreatedPostDto {

    @ApiModelProperty(value = "Uma String que representa o apelido do usuário", example = "Teste_Exemplo_123")
    private String user_email;

    @ApiModelProperty(value = "Uma String que representa data de nascimento do usuário", example = "2020-10-18")
    private String texto;

    public CreatedPostDto() {

    }

    public Posts toPost(UsersRepository usersRepository) {
        Posts post = new Posts();
        Content content = new Content();
        content.setContent(texto);
        post.setUsers(usersRepository.findByEmail(this.user_email));
        post.setContent(content);

        return post;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_email(){
        return this.user_email;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTexto(){
        return this.texto;
    }
}
