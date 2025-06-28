package com.jinouk.sca.domain.listreturn.service;

import com.jinouk.sca.domain.listreturn.dto.lrdto;
import com.jinouk.sca.domain.listreturn.dto.positiondto;
import com.jinouk.sca.domain.listreturn.entity.lrentity;
import com.jinouk.sca.domain.listreturn.repo.repo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class lrser {
    private final repo repo;

    public List<lrdto> getAllplaces() {
        List<lrentity> places = repo.findAll();
        return places.stream()
                .map(lrdto::tolrdto)
                .collect(Collectors.toList());
    }

    public List<positiondto> position(List<String> places) {
        List<lrentity> list = repo.findByNameIn(places);
        return list.stream()
                .map(positiondto::toPdto)
                .collect(Collectors.toList());
    }
}
