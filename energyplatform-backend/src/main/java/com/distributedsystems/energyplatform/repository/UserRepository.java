package com.distributedsystems.energyplatform.repository;

import com.distributedsystems.energyplatform.entity.Role;
import com.distributedsystems.energyplatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User getUserByUsername(String username);
    boolean existsByUsername(String username);
    List<User> findAllByRolesContains(Role role);
}
