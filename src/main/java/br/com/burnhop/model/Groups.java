package br.com.burnhop.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import java.sql.Timestamp;

@Entity
public class Groups {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id_groups;

    private String name;

    private String description;

    private Timestamp created_group_on;

    @ManyToOne(targetEntity=Users.class, fetch= FetchType.EAGER)
    @JoinColumn(name="admin_id")
    private Users admin;

    public Groups(){

    }

    public Groups(String name, String description, Timestamp created_group_on){
        this.name = name;
        this.description = description;
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

    public Timestamp getCreatedGroupOn() {
        return created_group_on;
    }

    public void setCreatedGroupOn(Timestamp created_group_on) {
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
        return "Nome: "+this.name+"\nCriador: "+this.getAdmin().getLogin().getEmail()+"\nCriado em: "+this.getCreatedGroupOn();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
