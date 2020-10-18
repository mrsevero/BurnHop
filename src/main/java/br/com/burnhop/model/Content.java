package br.com.burnhop.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String content;
}
