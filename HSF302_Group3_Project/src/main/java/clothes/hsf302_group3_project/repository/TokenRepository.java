package clothes.hsf302_group3_project.repository;

import clothes.hsf302_group3_project.entity.User;
import clothes.hsf302_group3_project.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByToken(String token);

    void deleteByUser(User user);

    @Modifying
    @Transactional
    @Query("DELETE FROM VerificationToken v WHERE v.expiryTime < :now")
    void deleteAllExpired(@Param("now") LocalDateTime now);
}
