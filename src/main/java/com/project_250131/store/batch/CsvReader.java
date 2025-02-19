package com.project_250131.store.batch;

import com.project_250131.store.domain.StoreDTO;
import com.project_250131.store.entity.StoreEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;

@RequiredArgsConstructor
@Configuration
public class CsvReader {

    private final StoreFieldSetMapper storeFieldSetMapper;

    @Bean
    public FlatFileItemReader<StoreDTO> csvStoreReader() {
        FlatFileItemReader<StoreDTO> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("/csv/store.csv"));
        reader.setEncoding("UTF-8");

        DefaultLineMapper<StoreDTO> defaultLineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(",");

        defaultLineMapper.setLineTokenizer(tokenizer);

        defaultLineMapper.setFieldSetMapper(storeFieldSetMapper);

        reader.setLineMapper(defaultLineMapper);

        return reader;
    }

}
