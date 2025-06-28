package com.jinouk.sca.domain.user.repository;

import com.jinouk.sca.domain.user.entity.userentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface userrepo extends JpaRepository<userentity, Integer> {
    Optional<userentity> findByUserid(String UserId);
}
