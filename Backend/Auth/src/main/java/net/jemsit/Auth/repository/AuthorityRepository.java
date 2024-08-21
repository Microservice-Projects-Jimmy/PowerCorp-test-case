package net.jemsit.Auth.repository;

import net.jemsit.Auth.entity.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<AuthorityEntity, String> {

}
