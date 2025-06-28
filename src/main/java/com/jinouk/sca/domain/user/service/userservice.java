package com.jinouk.sca.domain.user.service;

import com.jinouk.sca.domain.user.dto.userdto;
import com.jinouk.sca.domain.user.entity.userentity;
import com.jinouk.sca.domain.user.repository.userrepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class userservice {
    private final userrepo userrepo;

    public void save(userdto udto) {
        userentity uentity = userentity.touserentity(udto);
        userrepo.save(uentity);
    }

    public userentity login(String userid, String pw) {
        Optional<userentity> optionalUser = userrepo.findByUserid(userid);

        if (optionalUser.isPresent()) {
            userentity user = optionalUser.get();
            if (user.getPw().equals(pw)) {
                return user;
            }
        }
        return null;
    }
}
