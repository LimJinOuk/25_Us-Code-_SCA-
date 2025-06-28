package com.jinouk.sca.domain.user.service;

import com.jinouk.sca.domain.user.entity.userentity;
import com.jinouk.sca.domain.user.repository.userrepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class creditservice {

    private final userrepo userrepo;

    private userentity getLoginUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) throw new NullPointerException("세션 없음");

        Object userObj = session.getAttribute("loginUser");
        if (!(userObj instanceof userentity)) throw new NullPointerException("로그인 사용자 정보 없음");

        return (userentity) userObj;
    }

    public int getCredit(HttpServletRequest request) {
        userentity user = getLoginUser(request);
        return user.getCredit();
    }

    public void addCredit(HttpServletRequest request, int amount) {
        userentity user = getLoginUser(request);
        user.setCredit(user.getCredit() + amount);
        userrepo.save(user);
    }

    public void subtractCredit(HttpServletRequest request, int amount) {
        userentity user = getLoginUser(request);
        if (user.getCredit() < amount) {
            throw new IllegalArgumentException("잔액 부족");
        }
        user.setCredit(user.getCredit() - amount);
        userrepo.save(user);
    }
}