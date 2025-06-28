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
    private String name; //사용자 이름
    private String pw; //비번
    private String phone; //전화번호
    private String userid; //사용자 아이디
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
