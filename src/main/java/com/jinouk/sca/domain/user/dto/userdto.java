package com.jinouk.sca.domain.user.dto;

import com.jinouk.sca.domain.user.entity.userentity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class userdto {
    private int id;
    private String name;
    private String pw;
    private String phone;
    private String userid;
    private int credit;

    public static userdto touserdto(userentity uentity)
    {
        userdto udto = new userdto();

        udto.setId(uentity.getId());
        udto.setName(uentity.getName());
        udto.setPw(uentity.getPw());
        udto.setCredit(uentity.getCredit());
        udto.setPhone(uentity.getPhone());
        udto.setUserid(uentity.getUserid());

        return udto;
    }
}
