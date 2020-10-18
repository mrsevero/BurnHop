package br.com.burnhop.model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private Timestamp posted_on;

    @OneToMany(targetEntity=Users.class, fetch= FetchType.EAGER)
    @JoinColumn(name="user_id")
    private Users users;

    @OneToOne(targetEntity=Content.class, fetch= FetchType.EAGER)
    @JoinColumn(name="user_id")
    private Content content;

}
