package com.fabien.kata.fooBarQuix.batch.reader;

import com.fabien.kata.fooBarQuix.model.Input;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class FooBarQuixBatchReader {

    @Value("${file.input}")
    private String inputFilePath;


    @Bean
    public FlatFileItemReader<Input> itemReader() {
        return new FlatFileItemReaderBuilder<Input>()
                .name("inputItemReader")
                .resource(new ClassPathResource(inputFilePath))
                .delimited().names("number")
                .targetType(Input.class)
                .build();
    }
}
