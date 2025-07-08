package clothes.hsf302_group3_project.repository;

import clothes.hsf302_group3_project.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u from User u WHERE (u.name LIKE %:name% OR :name IS NULL) " +
            "AND (u.email LIKE %:email% OR :email IS NULL) " +
            "AND (u.phone LIKE %:phone% OR :phone IS NULL) " +
            "AND u.role = 'ADMIN'")
    Page<User> findAdmins(String name, String email, String phone, Pageable pageable);

    @Query("SELECT u from User u WHERE (u.name LIKE %:name% OR :name IS NULL) " +
            "AND (u.email LIKE %:email% OR :email IS NULL) " +
            "AND (u.phone LIKE %:phone% OR :phone IS NULL) " +
            "AND u.role = 'CUSTOMER'")
    Page<User> findCustomers(String name, String email, String phone, Pageable pageable);
}
