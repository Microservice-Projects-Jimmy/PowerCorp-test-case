package net.jemsit.Click;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClickRepository extends JpaRepository<ClickEntity, Long> {
    List<ClickEntity> findByUsername(String username);
}
