package br.com.burnhop.model.Dto;

import br.com.burnhop.model.Login;
import br.com.burnhop.model.User;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class UserDto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String username;

    private Date data_nasc;

    @OneToOne(targetEntity= Login.class, fetch=FetchType.EAGER)
    @JoinColumn(name="login_id")
    private Login login;

    public UserDto() {

    }

    public UserDto(String name, String username, Date data_nasc) {
        this.name = name;
        this.username = username;
        this.data_nasc = data_nasc;
    }

    public User toUser() {
        User user = new User();
        user.setName(this.name);
        user.setData_nasc(this.data_nasc);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    @Override
    public String toString(){
        return "Nome: "+this.getName()+"\nEmail: "+this.getLogin().getEmail()+"\nData de criação: "+this.getCreated_on();
    }
}
