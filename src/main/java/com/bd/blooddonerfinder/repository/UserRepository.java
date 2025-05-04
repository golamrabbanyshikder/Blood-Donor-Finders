package com.bd.blooddonerfinder.repository;

import com.bd.blooddonerfinder.model.Users;
import com.bd.blooddonerfinder.model.enums.BloodGroup;
import com.bd.blooddonerfinder.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findById(long id);
    Optional<Users> findByEmail(String email);
    List<Users> findByRoleAndBloodGroup(Role role, BloodGroup group);

}
