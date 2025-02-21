package com.project_250131.store.repository;

import com.project_250131.store.entity.StoreEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<StoreEntity, Integer> {

    public Page<StoreEntity> findAll(Pageable pageable);

    public Page<StoreEntity> findByRoadAddressContainingOrStreetAddressContaining
            (String roadRegion, String streetRegion, Pageable pageable);
    public int countByRoadAddressContainingOrStreetAddressContaining
            (String roadRegion, String streetRegion);

    public Page<StoreEntity> findByContinentContaining(String continent, Pageable pageable);
    public int countByContinentContaining(String continent);
}
