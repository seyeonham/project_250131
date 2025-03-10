package com.project_250131.store.batch;

import com.project_250131.store.domain.StoreDTO;
import com.project_250131.store.entity.StoreEntity;
import com.project_250131.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CsvItemWriter implements ItemWriter<StoreDTO> {

    private final StoreRepository storeRepository;

    @Override
    public void write(Chunk<? extends StoreDTO> items) throws Exception {
        List<StoreEntity> storeList = new ArrayList<>();

        items.forEach(getStoreDTO -> {
            StoreEntity store = getStoreDTO.toEntity();
            storeList.add(store);
        });

        storeRepository.saveAll(storeList);
    }
}
