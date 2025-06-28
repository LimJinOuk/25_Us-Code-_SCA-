package com.jinouk.sca.domain.listreturn.dto;

import com.jinouk.sca.domain.listreturn.entity.lrentity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class lrdto {
    private int id;
    private String name;
    private BigDecimal latitude;
    private BigDecimal longtitude;
    private String addr;
    private int tag;

    public static lrdto tolrdto(lrentity lrentity) {
        lrdto lrdto = new lrdto();
        lrdto.setId(lrentity.getId());
        lrdto.setName(lrentity.getName());
        lrdto.setLongtitude(lrentity.getLongtitude());
        lrdto.setLatitude(lrentity.getLatitude());
        lrdto.setAddr(lrentity.getAddr());
        lrdto.setTag(lrentity.getTag());

        return lrdto;
    }
}
