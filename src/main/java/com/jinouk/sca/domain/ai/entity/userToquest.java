package com.jinouk.sca.domain.ai.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Table(name = "userToquest")
@Getter@Setter
@Entity
public class userToquest {
    // getters/setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int userid;
    @Column
    private int questid;

    public void setUserid(int userid) { this.userid = userid; }

    public void setQuestid(int questid) { this.questid = questid; }
}
