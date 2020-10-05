package br.com.burnhop.model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String username;

    private Date data_nasc;

    private Timestamp created_on;

    @OneToOne(targetEntity=Login.class, fetch=FetchType.EAGER)
    @JoinColumn(name="login_id")
    private Login login;

    public Users () {
        
    }

    public Users(String name, String username, Date data_nasc, Timestamp created_on) {
        this.name = name;
        this.username = username;
        this.data_nasc = data_nasc;
        this.created_on = created_on;
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

    public Timestamp getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Timestamp created_on) {
        this.created_on = created_on;
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
