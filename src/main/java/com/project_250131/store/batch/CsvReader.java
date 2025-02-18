package com.project_250131.store.batch;

import com.project_250131.store.entity.StoreEntity;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class CsvReader {

    @Bean
    public FlatFileItemReader<StoreEntity> csvStoreReader() {
        FlatFileItemReader<StoreEntity> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("/csv/store.csv"));
        reader.setEncoding("UTF-8");

        DefaultLineMapper<StoreEntity> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(",");
        tokenizer.setNames("CTGRY_TWO_NM", "TEL_NO", "RDNMADR_NM", "LNM_ADDR", "LC_LA", "LC_LO", "WORKDAY_OPER_TIME_DC", "WKEND_OPER_TIME_DC");
        lineMapper.setLineTokenizer(tokenizer);

        lineMapper.setFieldSetMapper(new StoreFieldSetMapper());

        reader.setLineMapper(lineMapper);
        return reader;
    }
}
