package com.fabien.kata.fooBarQuix.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;


@Component
public class JobCompletionNotificationListener implements JobExecutionListener {
    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("--- JOB STARTED ---");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("--- JOB COMPLETED ---");
        }
    }
}
