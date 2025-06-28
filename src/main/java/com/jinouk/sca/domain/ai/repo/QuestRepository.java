package com.jinouk.sca.domain.ai.repo;

import com.jinouk.sca.domain.ai.entity.quest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface QuestRepository extends JpaRepository<quest, Long> {
    Optional<quest> findByLocate(String locate); // GeminiService용
}