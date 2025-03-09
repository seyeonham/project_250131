package com.project_250131.store.repository;

import com.project_250131.store.domain.StoreDTO;
import com.project_250131.store.entity.StoreEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<StoreEntity, Integer> {

    public Page<StoreEntity> findAll(Pageable pageable);

    public List<StoreEntity> findByRoadAddressContainingOrStreetAddressContaining
            (String roadRegion, String streetRegion);
    public int countByRoadAddressContainingOrStreetAddressContaining
            (String roadRegion, String streetRegion);

    public Page<StoreEntity> findByContinentContaining(String continent, Pageable pageable);
    public int countByContinentContaining(String continent);

    public Page<StoreEntity> findByStoreNameContaining(String name, Pageable pageable);
    public int countByStoreNameContaining(String name);

    public Page<StoreEntity> findById(int userId, Pageable pageable);
}
