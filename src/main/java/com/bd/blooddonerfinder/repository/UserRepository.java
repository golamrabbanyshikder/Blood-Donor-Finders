package com.bd.blooddonerfinder.repository;

import com.bd.blooddonerfinder.model.Users;
import com.bd.blooddonerfinder.model.enums.BloodGroup;
import com.bd.blooddonerfinder.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findById(long id);
    Optional<Users> findByEmail(String email);
    List<Users> findByRoleAndBloodGroup(Role role, BloodGroup group);
    @Query(value = "SELECT COUNT(id) FROM users u WHERE u.id <> :id AND u.name = :name", nativeQuery = true)
    Long checkUniqueName(@Param("id") Long id, @Param("name") String name);
    boolean existsById(Long id);

}
