package com.project_250131.store.batch;

import com.project_250131.store.domain.StoreDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@RequiredArgsConstructor
@Configuration
public class CsvReader {

    private final StoreFieldSetMapper storeFieldSetMapper;

    @Bean
    public FlatFileItemReader<StoreDTO> csvStoreReader() {
        FlatFileItemReader<StoreDTO> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("csv/store.csv"));
        reader.setEncoding("UTF-8");
        reader.setLinesToSkip(1);

        DefaultLineMapper<StoreDTO> defaultLineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(",");
        tokenizer.setNames("FCLTY_NM", "CTGRY_TWO_NM", "LC_LA", "LC_LO", "RDNMADR_NM", "LNM_ADDR", "TEL_NO", "WORKDAY_OPER_TIME_DC", "WKEND_OPER_TIME_DC");
        tokenizer.setIncludedFields(0, 2, 11, 12, 14, 15, 16, 17, 18);
        tokenizer.setStrict(false);

        defaultLineMapper.setLineTokenizer(tokenizer);

        defaultLineMapper.setFieldSetMapper(storeFieldSetMapper);

        reader.setLineMapper(defaultLineMapper);

        return reader;
    }

}
