package com.fabien.kata.fooBarQuix.integration;

import com.fabien.kata.fooBarQuix.model.FooBarQuixResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class FooBarQuixIntegrationTest {
    private static final String OUTPUT_FILE_PATH="temp/test-files/output.csv";

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Test
    void testJobExecutionWithOutputVerification() throws Exception{
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(job,jobParameters);

        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");

        File outputFile = new File(OUTPUT_FILE_PATH);
        assertThat(outputFile.exists()).isTrue();

        List<String> lines = Files.readAllLines(outputFile.toPath());
        assertThat(lines).isNotEmpty();

        FooBarQuixResult expectedFooBarQuixResult0 = new FooBarQuixResult(0, "FOOBAR");
        assertThat(lines.get(0)).isEqualTo(expectedFooBarQuixResult0.toString());
        FooBarQuixResult expectedFooBarQuixResult3 = new FooBarQuixResult(3, "FOOFOO");
        assertThat(lines.get(3)).isEqualTo(expectedFooBarQuixResult3.toString());
        FooBarQuixResult expectedFooBarQuixResult5 = new FooBarQuixResult(5, "BARBAR");
        assertThat(lines.get(5)).isEqualTo(expectedFooBarQuixResult5.toString());
        FooBarQuixResult expectedFooBarQuixResult7 = new FooBarQuixResult(7, "QUIX");
        assertThat(lines.get(7)).isEqualTo(expectedFooBarQuixResult7.toString());
    }

    @AfterEach
    void cleanup() {
        File outputFile = new File(OUTPUT_FILE_PATH);
        if (outputFile.exists()) {
            outputFile.delete();
        }
    }
}
