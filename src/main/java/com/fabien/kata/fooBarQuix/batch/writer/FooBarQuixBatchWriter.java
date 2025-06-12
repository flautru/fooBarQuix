package com.fabien.kata.fooBarQuix.batch.writer;

import com.fabien.kata.fooBarQuix.model.FooBarQuixResult;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;

@Configuration
public class FooBarQuixBatchWriter {

    @Value("${file.output}")
    private String outputFilePath;

    @Bean
    public FlatFileItemWriter<FooBarQuixResult> itemWriter() {
        return new FlatFileItemWriterBuilder<FooBarQuixResult>()
                .name("itemWriter")
                .resource(new PathResource(outputFilePath))
                .lineAggregator(new PassThroughLineAggregator<>())
                .build();
    }
}
