package com.project_250131.user.repository;

import com.project_250131.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByLoginId(String loginId);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByNameAndEmail(String name, String email);

    Optional<UserEntity> findByLoginIdAndEmail(String loginId, String email);
}
