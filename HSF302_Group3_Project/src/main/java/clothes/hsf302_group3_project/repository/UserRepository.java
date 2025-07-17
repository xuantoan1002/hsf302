package clothes.hsf302_group3_project.repository;

import clothes.hsf302_group3_project.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import clothes.hsf302_group3_project.enums.OrderStatus;

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
            "AND (u.role = 'CUSTOMER' OR u.role = 'SHIPPER')")
    Page<User> findCustomers(String name, String email, String phone, Pageable pageable);

    @Query("SELECT u from User u WHERE (u.name LIKE %:name% OR :name IS NULL) " +
            "AND (u.email LIKE %:email% OR :email IS NULL) " +
            "AND (u.phone LIKE %:phone% OR :phone IS NULL) " +
            "AND u.role = 'SHIPPER'")
    Page<User> findShippers(String name, String email, String phone, Pageable pageable);

    @Query("SELECT u from User u " +
            "JOIN Order o ON o.shipper.id = u.id " +
            "WHERE NOT EXISTS (" +
            "            SELECT 1 FROM Order o " +
            "            WHERE o.shipper.id = u.id AND o.status = :status " +
            "        )" +
            "AND (:name IS NULL OR u.name LIKE CONCAT('%', :name, '%'))" +
            "AND (:email IS NULL OR u.email LIKE CONCAT('%', :email, '%'))" +
            "AND (:phone IS NULL OR u.phone LIKE CONCAT('%', :phone, '%'))" +
            "GROUP BY u")
    Page<User> findAvailableShipper(String email, String name, String phone, OrderStatus status, Pageable pageable);

}
