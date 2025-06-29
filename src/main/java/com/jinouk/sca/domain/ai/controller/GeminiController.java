package com.jinouk.sca.domain.ai.controller;

import com.jinouk.sca.domain.ai.service.extractAIService;
import com.jinouk.sca.domain.ai.service.GeminiService;
import com.jinouk.sca.domain.user.entity.userentity;

import com.jinouk.sca.domain.user.service.creditservice;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/quest")
@RequiredArgsConstructor
public class GeminiController {

    private final GeminiService geminiService;
    private final creditservice creditservice;


    @PostMapping("/verify-quest")
    public ResponseEntity<?> verifyQuest(@RequestParam("image") MultipartFile image,
                                         @RequestParam(value = "keyword", required = false, defaultValue = "의성군청") String keyword,
                                         HttpSession session,
                                         HttpServletRequest request) {
        Object userObj = session.getAttribute("loginUser");

        if (!(userObj instanceof userentity)) {
            return ResponseEntity.status(401).body(Map.of("error", "로그인이 필요합니다."));
        }

        try {
            String result = geminiService.verifyQuestImage(image, keyword);
            if (Objects.equals(result, "정말 잘 하셨네요! 정답이에요!")) {
                creditservice.addCredit(request, 3000);
            }
            System.out.println("Gemini 결과: " + result);
            return ResponseEntity.ok(Map.of("response", result));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "이미지 판별 중 오류: " + e.getMessage()));
        }
    }
}
