package com.project_250131.store.batch;

import com.project_250131.store.entity.StoreEntity;
import com.project_250131.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CsvItemWriter implements ItemWriter<StoreEntity> {

    private final StoreRepository storeRepository;

    @Override
    public void write(Chunk<? extends StoreEntity> chunk) throws Exception {
        storeRepository.saveAll(chunk);
    }
}
