package com.jinouk.sca.domain.listreturn.controller;

import com.jinouk.sca.domain.listreturn.dto.lrdto;
import com.jinouk.sca.domain.listreturn.dto.positiondto;
import com.jinouk.sca.domain.listreturn.service.lrser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class lrcont {
    private final lrser lrser;

    @GetMapping("/getallplace")
    public ResponseEntity<List<lrdto>> getAllPlaces() {
        List<lrdto> places = lrser.getAllplaces();
        return ResponseEntity.ok(places);
    }

    @PostMapping("/selections")
    public ResponseEntity<List<positiondto>> selections(@RequestBody List<String> places) {
        List<positiondto> Plist = lrser.position(places);
        return ResponseEntity.ok(Plist);
    }
}
