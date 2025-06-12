package com.fabien.kata.fooBarQuix.config;

import com.fabien.kata.fooBarQuix.batch.reader.FooBarQuixBatchReader;
import com.fabien.kata.fooBarQuix.batch.writer.FooBarQuixBatchWriter;
import com.fabien.kata.fooBarQuix.model.FooBarQuixResult;
import com.fabien.kata.fooBarQuix.model.Input;
import com.fabien.kata.fooBarQuix.batch.processor.FooBarQuixBatchProcessor;
import com.fabien.kata.fooBarQuix.batch.listener.JobCompletionNotificationListener;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class FooBarQuixBatchConfig {

    private final FooBarQuixBatchReader fooBarQuixBatchReader;
    private final FooBarQuixBatchWriter fooBarQuixBatchWriter;
    private final FooBarQuixBatchProcessor fooBarQuixBatchProcessor;

    public FooBarQuixBatchConfig(FooBarQuixBatchReader fooBarQuixBatchReader, FooBarQuixBatchWriter fooBarQuixBatchWriter, FooBarQuixBatchProcessor fooBarQuixBatchProcessor) {
        this.fooBarQuixBatchReader = fooBarQuixBatchReader;
        this.fooBarQuixBatchWriter = fooBarQuixBatchWriter;
        this.fooBarQuixBatchProcessor = fooBarQuixBatchProcessor;
    }

    @Bean
    public Job fooBarQuixJob(JobRepository jobRepository, Step step1, JobCompletionNotificationListener listener) {
        return new JobBuilder("fooBarQuixJob", jobRepository)
                .listener(listener)
                .start(step1)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step1", jobRepository)
                .<Input, FooBarQuixResult>chunk(1, transactionManager)
                .reader(fooBarQuixBatchReader.itemReader())
                .processor(fooBarQuixBatchProcessor)
                .writer(fooBarQuixBatchWriter.itemWriter())
                .build();
    }
}
