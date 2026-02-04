package com.example.hellow_world.repository;


import com.example.hellow_world.model.Role;
import com.example.hellow_world.model.UserSec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleEntityByRole(String rolename);

}

