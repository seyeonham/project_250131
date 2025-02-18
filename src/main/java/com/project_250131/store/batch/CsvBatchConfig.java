package com.project_250131.store.batch;

import com.project_250131.store.domain.StoreDTO;
import com.project_250131.store.repository.StoreRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
@EnableBatchProcessing
public class CsvBatchConfig {

    private final JobLauncher jobLauncher;
    private final CsvReader csvReader;
    private final CsvItemWriter csvItemWriter;
    private final StoreRepository storeRepository;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Step step() {
        return new StepBuilder("step", jobRepository)
                .<StoreDTO, StoreDTO> chunk(1000)
                .reader(csvReader.csvStoreReader())
                .writer(csvItemWriter)
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    public Job job() {
        return new JobBuilder("job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(step())
                .end()
                .build();
    }

    @Bean
    public CommandLineRunner importCsvIfNotExist() {
        return args -> {
            if (storeRepository.count() == 0) {
                System.out.println("DB에 데이터가 없으므로 CSV 파일을 DB에 저장합니다.");
                try {
                    jobLauncher.run(job(), new JobParameters());  // 배치 작업 실행
                    System.out.println("배치 작업 실행 완료");
                } catch (Exception e) {
                    System.out.println("배치 작업 실행 중 오류 발생: " + e.getMessage());
                }
            } else {
                System.out.println("DB에 이미 데이터가 존재합니다. CSV 파일을 저장하지 않습니다.");
            }
        };
    }

}
