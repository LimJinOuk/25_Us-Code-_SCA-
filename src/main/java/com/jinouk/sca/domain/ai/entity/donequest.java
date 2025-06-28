package com.jinouk.sca.domain.ai.entity;


import jakarta.persistence.*;

@Table
@Entity
public class donequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int userid;

    @Column
    private int acceptedquestid;
}
