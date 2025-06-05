package com.fabien.kata.fooBarQuix.config;

import com.fabien.kata.fooBarQuix.model.FooBarQuixResult;
import com.fabien.kata.fooBarQuix.model.Input;
import com.fabien.kata.fooBarQuix.service.batch.FooBarQuixBatchProcessor;
import com.fabien.kata.fooBarQuix.service.batch.JobCompletionNotificationListener;
import com.fabien.kata.fooBarQuix.service.FooBarQuixService;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class FooBarQuixBatchConfig {

    private final FooBarQuixService fooBarQuixService;

    public FooBarQuixBatchConfig(FooBarQuixService fooBarQuixService) {
        this.fooBarQuixService = fooBarQuixService;
    }

    @Bean
    public FlatFileItemReader<Input> reader() {
        return new FlatFileItemReaderBuilder<Input>()
                .name("inputItemReader")
                .resource(new ClassPathResource("static/input_numbers.txt"))
                .delimited().names("number")
                .targetType(Input.class)
                .build();
    }

    @Bean
    public FooBarQuixBatchProcessor processor() {
        return new FooBarQuixBatchProcessor(fooBarQuixService);
    }

    @Bean
    public FlatFileItemWriter<FooBarQuixResult> itemWriter() {
        return new FlatFileItemWriterBuilder<FooBarQuixResult>()
                .name("itemWriter")
                .resource(new PathResource("temp/test-files/output.csv"))
                .lineAggregator(new PassThroughLineAggregator<>())
                .build();
    }

    @Bean
    public Job fooBarQuixJob(JobRepository jobRepository, Step step1, JobCompletionNotificationListener listener) {
        return new JobBuilder("fooBarQuixJob", jobRepository)
                .listener(listener)
                .start(step1)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                      FlatFileItemReader<Input> reader, FooBarQuixBatchProcessor processor, FlatFileItemWriter<FooBarQuixResult> writer) {
        return new StepBuilder("step1", jobRepository)
                .<Input, FooBarQuixResult>chunk(1, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
