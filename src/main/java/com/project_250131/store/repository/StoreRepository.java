package com.project_250131.store.repository;

import com.project_250131.store.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<StoreEntity, Integer> {

    List<StoreEntity> findAll();

    List<StoreEntity> findByRoadAddressContainingOrStreetAddressContaining
            (String roadRegion, String streetRegion);
    int countByRoadAddressContainingOrStreetAddressContaining
            (String roadRegion, String streetRegion);

    List<StoreEntity> findByContinentContaining(String continent);
    int countByContinentContaining(String continent);

    List<StoreEntity> findByStoreNameContaining(String name);
    int countByStoreNameContaining(String name);

    Optional<StoreEntity> findById(int userId);
}
