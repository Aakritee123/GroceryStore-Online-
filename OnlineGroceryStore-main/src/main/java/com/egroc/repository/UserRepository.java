package com.egroc.repository;

import com.egroc.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {


    Users findByUsername(String username);

    Optional<Users> findByEmail(String email);

    Users findByUserId(Long id);


}


