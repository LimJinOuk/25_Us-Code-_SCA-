package com.jinouk.sca.domain.listreturn.dto;

import com.jinouk.sca.domain.listreturn.entity.lrentity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class positiondto {
    private String name;
    private BigDecimal latitude;
    private BigDecimal longtitude;

    public static positiondto toPdto(lrentity lrentity) {
        positiondto Pdto = new positiondto();
        Pdto.setName(lrentity.getName());
        Pdto.setLatitude(lrentity.getLatitude());
        Pdto.setLongtitude(lrentity.getLongtitude());
        return Pdto;
    }

    @Override
    public String toString() {
        return "positiondto{" +
                "name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longtitude=" + longtitude +
                '}';
    }
}
