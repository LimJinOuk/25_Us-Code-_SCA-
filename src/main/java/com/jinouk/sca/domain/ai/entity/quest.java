package com.jinouk.sca.domain.ai.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
public class quest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String locate;
    private String quest;

    // getters/setters
    public int getId() { return id; }
    public String getLocate() { return locate; }
    public void setLocate(String locate) { this.locate = locate; }

    public String getQuest() { return quest; }
    public void setQuest(String quest) { this.quest = quest; }
}
