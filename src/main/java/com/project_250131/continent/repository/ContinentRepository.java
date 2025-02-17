package com.project_250131.continent.repository;

import com.project_250131.continent.entity.ContinentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContinentRepository extends JpaRepository<ContinentEntity, Integer> {
}
