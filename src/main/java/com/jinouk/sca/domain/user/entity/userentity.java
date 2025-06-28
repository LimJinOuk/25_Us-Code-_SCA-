package com.jinouk.sca.domain.user.entity;

import com.jinouk.sca.domain.user.dto.userdto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "userentity")
public class userentity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String userid; //로그인시 사용

    @Column
    private String pw;

    @Column
    private String phone;

    @Column
    private int credit;

    public static userentity touserentity(userdto udto)
    {
        userentity uentity = new userentity();

        uentity.setId(udto.getId());
        uentity.setName(udto.getName());
        uentity.setPw(udto.getPw());
        uentity.setCredit(udto.getCredit());
        uentity.setPhone(udto.getPhone());
        uentity.setUserid(udto.getUserid());

        return uentity;
    }

    public static userentity creditupdate(userdto dto)
    {
        userentity uentity = new userentity();

        uentity.setCredit(dto.getCredit());

        return uentity;
    }
}
