package br.com.burnhop.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import java.sql.Timestamp;

@Entity
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id_post;

    private Timestamp posted_on;

    @ManyToOne(targetEntity=Users.class, fetch= FetchType.EAGER)
    @JoinColumn(name="user_id")
    private Users users;

    @ManyToOne(targetEntity=Groups.class, fetch= FetchType.EAGER)
    @JoinColumn(name="group_id")
    private Groups group;

    @OneToOne(targetEntity=Content.class, fetch= FetchType.EAGER)
    @JoinColumn(name="content_id")
    private Content content;

    public int getId() {
        return id_post;
    }

    public void setId(int id_post) {
        this.id_post = id_post;
    }

    public Timestamp getPostedOn() {
        return posted_on;
    }

    public void setPostedOn(Timestamp posted_on) {
        this.posted_on = posted_on;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Groups getGroup() {
        return group;
    }

    public void setGroup(Groups group) {
        this.group = group;
    }
}
