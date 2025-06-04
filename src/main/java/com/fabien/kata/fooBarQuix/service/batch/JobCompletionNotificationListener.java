package com.fabien.kata.fooBarQuix.service.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


@Component
public class JobCompletionNotificationListener implements JobExecutionListener {
    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution){
        log.info("--- JOB STARTED ---");
    }

    @Override
    public void afterJob(JobExecution jobExecution){
        if(jobExecution.getStatus() == BatchStatus.COMPLETED){
            log.info("--- JOB COMPLETED ---");

            Resource resource = new PathResource("temp/test-files/output.csv");
            if (resource.exists()) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        log.info("Read line: {}", line);
                    }
                } catch ( IOException e) {
                    log.error("Error reading file", e);
                }
            } else {
                log.warn("File does not exist");
            }
        }
    }
}
