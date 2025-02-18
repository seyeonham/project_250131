package com.project_250131.store.batch;

import com.project_250131.store.entity.StoreEntity;
import com.project_250131.store.repository.StoreRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class CsvBatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CsvReader csvReader;
    private final CsvItemWriter csvItemWriter;
    private final StoreRepository storeRepository;

    @Bean
    public Step step() {
        return stepBuilderFactory.get("step")
                .<StoreEntity, StoreEntity> chunk(1000)
                .reader(csvReader.csvStoreReader())
                .writer(csvItemWriter)
                .build();
    }

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                .incrementer(new RunIdIncrementer())
                .flow(step())
                .end()
                .build();
    }

    @PostConstruct
    public void importCsvIfNotExist() {
        if (storeRepository.count() == 0) {
            System.out.println("DB에 데이터가 없으므로 CSV 파일을 DB에 저장합니다.");
            job();
        } else {
            System.out.println("DB에 이미 데이터가 존재합니다. CSV 파일을 저장하지 않습니다.");
        }
    }
}
