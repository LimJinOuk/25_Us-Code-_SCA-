package com.jinouk.sca.domain.user.controller;


import com.jinouk.sca.domain.user.entity.userentity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class userinfocontroller {
    @GetMapping("/api/userinfo")
    public ResponseEntity<?> getUserInfo(@SessionAttribute(value = "loginUser", required = false) userentity user) {
        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("error", "로그인이 필요합니다."));
        }

        return ResponseEntity.ok(Map.of(
                "id", user.getId(),
                "name", user.getName(),
                "phone", user.getPhone(),
                "userid", user.getUserid(),
                "credit", user.getCredit()// ← 추가

        ));
    }
}
