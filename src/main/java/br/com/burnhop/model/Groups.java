package br.com.burnhop.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Groups {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id_groups;

    private String name;

    private Timestamp created_group_on;

    @ManyToOne(targetEntity=Users.class, fetch= FetchType.EAGER)
    @JoinColumn(name="admin_id")
    private Users admin;

    public Groups(){

    }

    public Groups(String name, Timestamp created_group_on){
        this.name = name;
        this.created_group_on = created_group_on;
    }

    public int getId() {
        return id_groups;
    }

    public void setId(int id_groups) {
        this.id_groups = id_groups;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreated_group_on() {
        return created_group_on;
    }

    public void setCreated_group_on(Timestamp created_group_on) {
        this.created_group_on = created_group_on;
    }

    public Users getAdmin() {
        return admin;
    }

    public void setAdmin(Users admin) {
        this.admin = admin;
    }

    @Override
    public String toString(){
        return "Nome: "+this.name+"\nCriador: "+this.getAdmin().getLogin().getEmail()+"\nCriado em: "+this.getCreated_group_on();
    }
}
