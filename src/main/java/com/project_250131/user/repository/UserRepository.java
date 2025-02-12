package com.project_250131.user.repository;

import com.project_250131.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    public Optional<UserEntity> findByLoginId(String loginId);

    public Optional<UserEntity> findByEmail(String email);
}
