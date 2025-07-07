package clothes.hsf302_group3_project.repository;

import clothes.hsf302_group3_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
