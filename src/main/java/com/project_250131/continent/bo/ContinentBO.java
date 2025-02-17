package com.project_250131.continent.bo;

import com.project_250131.continent.entity.ContinentEntity;
import com.project_250131.continent.repository.ContinentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ContinentBO {

    private final ContinentRepository continentRepository;

    public List<ContinentEntity> getContinentEntityList() {
        return continentRepository.findAll();
    }
}
