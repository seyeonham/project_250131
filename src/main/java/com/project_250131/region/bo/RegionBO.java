package com.project_250131.region.bo;

import com.project_250131.region.entity.RegionEntity;
import com.project_250131.region.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RegionBO {

    private final RegionRepository regionRepository;

    public List<RegionEntity> getRegionList() {
        return regionRepository.findAll();
    }
}
