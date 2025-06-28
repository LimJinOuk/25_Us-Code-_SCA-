package com.jinouk.sca.domain.user.controller;

import com.jinouk.sca.domain.user.service.creditservice;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/credit")
@RequiredArgsConstructor
public class creditcontroller {

    private final creditservice creditService;

    @GetMapping
    public ResponseEntity<?> getCredit(HttpServletRequest request) {
        try {
            int credit = creditService.getCredit(request);
            return ResponseEntity.ok(Map.of("credit", credit));
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "로그인이 필요합니다."));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCredit(HttpServletRequest request, @RequestBody Map<String, Integer> body) {
        try {
            int amount = body.getOrDefault("amount", 0);
            creditService.addCredit(request, amount);
            return ResponseEntity.ok(Map.of("status", "ok"));
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "로그인이 필요합니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "추가 실패: " + e.getMessage()));
        }
    }

    @PostMapping("/subtract")
    public ResponseEntity<?> subtractCredit(HttpServletRequest request, @RequestBody Map<String, Integer> body) {
        try {
            int amount = body.getOrDefault("amount", 0);
            creditService.subtractCredit(request, amount);
            return ResponseEntity.ok(Map.of("status", "ok"));
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "로그인이 필요합니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "차감 실패: " + e.getMessage()));
        }
    }
}
