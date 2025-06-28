package com.jinouk.sca.domain.listreturn.repo;

import com.jinouk.sca.domain.listreturn.entity.lrentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface repo extends JpaRepository<lrentity, Long> {
    List<lrentity> findByNameIn(List<String> names);
}
