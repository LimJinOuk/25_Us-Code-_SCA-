package com.jinouk.sca.domain.user.controller;

import com.jinouk.sca.domain.user.dto.userdto;
import com.jinouk.sca.domain.user.entity.userentity;
import com.jinouk.sca.domain.user.repository.userrepo;
import com.jinouk.sca.domain.user.service.userservice;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class usercontroller {
    private final userrepo userrepo;
    private final userservice userservice;

    @PostMapping("/do_Register")
    public ResponseEntity<Map<String, String>> save(@RequestBody userdto userdto) {
        Map<String, String> map = new HashMap<>();
        if (userrepo.findByUserid(userdto.getUserid()).isEmpty()) {
            userservice.save(userdto);
            map.put("status", "success");
        }
        return ResponseEntity.ok(map);
    }


    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Map<String, String>> login(@RequestBody userdto userdto, HttpSession session) {
        userentity user = userservice.login(userdto.getName(), userdto.getPw());

        Map<String, String> map = new HashMap<>();

        if (user != null) {
            session.setAttribute("loginUser", user);

            map.put("status", "success");
        } else {
            map.put("status", "fail");
            map.put("message", "아이디 또는 비밀번호 불일치");
            return ResponseEntity.status(401).body(map);
        }
        return ResponseEntity.ok(map);

    }

    @GetMapping("/logout")
    public String logout(HttpSession session)
    {
        session.invalidate();
        Map<String, String> map = new HashMap<>();
        return "mainpage";
    }

    @GetMapping("/userid")
    public int userid(HttpSession session) {
        return (int) session.getAttribute("loginUser");
    }

}