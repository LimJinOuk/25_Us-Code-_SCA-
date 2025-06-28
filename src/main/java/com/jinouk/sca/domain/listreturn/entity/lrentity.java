package com.jinouk.sca.domain.listreturn.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "landmark")
public class lrentity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column
    private String name;

    @Column(precision = 8 , scale = 6)
    private BigDecimal latitude;

    @Column(precision = 9,scale = 6)
    private BigDecimal longtitude;

    @Column
    private String addr;

    @Column
    private int tag;

    @Override
    public String toString() {
        return "lrentity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longtitude=" + longtitude +
                ", addr='" + addr + '\'' +
                ", tag=" + tag +
                '}';
    }
}
