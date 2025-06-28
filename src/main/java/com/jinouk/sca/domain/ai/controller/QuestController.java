package com.jinouk.sca.domain.ai.controller;

import com.jinouk.sca.domain.ai.service.extractAIService;
import com.jinouk.sca.domain.user.entity.userentity; // ← 실제 User 클래스
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/quest")
@RequiredArgsConstructor
public class QuestController {

    private final extractAIService extractAIService;

    @PostMapping("/extract")
    public ResponseEntity<?> extract(@RequestParam String sen, HttpSession session) {
        Object userObj = session.getAttribute("loginUser");

        if (!(userObj instanceof userentity)) {
            return ResponseEntity.status(401).body(Map.of("error", "로그인이 필요합니다."));
        }

        userentity user = (userentity) userObj;
        int userId = user.getId(); // userentity에 getId() 있어야 함

        try {
            Map<String, String> result = extractAIService.extractLocateAndSave(sen, userId);
            return ResponseEntity.ok(Map.of("response", result));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", "처리 중 오류: " + e.getMessage()));
        }
    }
}
