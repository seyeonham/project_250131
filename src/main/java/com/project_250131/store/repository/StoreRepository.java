package com.project_250131.store.repository;

import com.project_250131.store.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<StoreEntity, Integer> {

    public List<StoreEntity> findByRoadAddressContainingOrStreetAddressContaining(String roadRegion, String streetRegion);
    public int countByRoadAddressContainingOrStreetAddressContaining(String roadRegion, String streetRegion);

    public List<StoreEntity> findByContinentContaining(String continent);
    public int countByContinent(String continent);
}
