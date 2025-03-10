package com.project_250131.store.repository;

import com.project_250131.store.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<StoreEntity, Integer> {

    public List<StoreEntity> findAll();

    public List<StoreEntity> findByRoadAddressContainingOrStreetAddressContaining
            (String roadRegion, String streetRegion);
    public int countByRoadAddressContainingOrStreetAddressContaining
            (String roadRegion, String streetRegion);

    public List<StoreEntity> findByContinentContaining(String continent);
    public int countByContinentContaining(String continent);

    public List<StoreEntity> findByStoreNameContaining(String name);
    public int countByStoreNameContaining(String name);

    public Optional<StoreEntity> findById(int userId);
}
