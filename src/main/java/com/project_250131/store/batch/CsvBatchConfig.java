package com.project_250131.store.batch;

import com.project_250131.store.domain.StoreDTO;
import com.project_250131.store.repository.StoreRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@EnableBatchProcessing
@RequiredArgsConstructor
@Configuration
public class CsvBatchConfig {

    private final CsvItemWriter csvItemWriter;
    private final CsvReader csvReader;
    private final JobLauncher jobLauncher;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job dataLoadJob() {
        return new JobBuilder("dataLoadJob", jobRepository)
                .start(dataLoadStep())
                .build();
    }

    @Bean
    public Step dataLoadStep() {
        return new StepBuilder("step", jobRepository)
                .<StoreDTO, StoreDTO>chunk(1000, transactionManager)
                .reader(csvReader.csvStoreReader())
                .writer(csvItemWriter)
                .build();
    }

    @Bean
    public CommandLineRunner runBatchJobAtStartup() {
        return args -> {
            try {
                JobParameters jobParameters = new JobParameters();
                jobLauncher.run(dataLoadJob(), jobParameters);
            } catch (Exception e) {
                log.error("Batch job failed", e);
            }
        };
    }
}
