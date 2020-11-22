package br.com.burnhop.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "usersgroups")
public class UsersGroups {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id_usersgroups;

    @ManyToOne(targetEntity=Groups.class, fetch= FetchType.EAGER)
    @JoinColumn(name="group_id")
    private Groups group;

    @ManyToOne(targetEntity=Users.class, fetch= FetchType.EAGER)
    @JoinColumn(name="user_id")
    private Users user;

    public UsersGroups(){

    }

    public int getIdUsersGroups() {
        return id_usersgroups;
    }

    public void setIdUsersGroups(int id_usersgroups) {
        this.id_usersgroups = id_usersgroups;
    }

    public Groups getGroup() {
        return group;
    }

    public void setGroup(Groups group) {
        this.group = group;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
