package com.jinouk.sca.domain.ai.controller;

import com.jinouk.sca.domain.ai.service.extractAIService;
import com.jinouk.sca.domain.ai.service.GeminiService;
import com.jinouk.sca.domain.user.entity.userentity;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/quest")
@RequiredArgsConstructor
public class GeminiController {

    private final GeminiService geminiService;

    // ✅ 이미지 + 키워드 기반 검증
    @PostMapping("/verify-quest")
    public ResponseEntity<?> verifyQuest(@RequestParam("image") MultipartFile image,
                                         @RequestParam(value = "keyword", required = false, defaultValue = "의성군청") String keyword,
                                         HttpSession session) {
        Object userObj = session.getAttribute("loginUser");

        if (!(userObj instanceof userentity)) {
            return ResponseEntity.status(401).body(Map.of("error", "로그인이 필요합니다."));
        }

        try {
            String result = geminiService.verifyQuestImage(image, keyword);
            return ResponseEntity.ok(Map.of("response", result));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "이미지 판별 중 오류: " + e.getMessage()));
        }
    }
}
