package com.project_250131.store.batch;

import com.project_250131.store.domain.StoreDTO;
import com.project_250131.store.entity.StoreEntity;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class CsvReader {

    @Bean
    public FlatFileItemReader<StoreDTO> csvStoreReader() {
        FlatFileItemReader<StoreDTO> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("csv/store.csv"));
        reader.setEncoding("UTF-8");

        DefaultLineMapper<StoreDTO> defaultLineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(",");
        tokenizer.setNames("CTGRY_TWO_NM", "TEL_NO", "RDNMADR_NM", "LNM_ADDR", "LC_LA", "LC_LO", "WORKDAY_OPER_TIME_DC", "WKEND_OPER_TIME_DC");
        defaultLineMapper.setLineTokenizer(tokenizer);

        BeanWrapperFieldSetMapper<StoreDTO> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(StoreDTO.class);

        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        reader.setLineMapper(defaultLineMapper);

        return reader;
    }

}
