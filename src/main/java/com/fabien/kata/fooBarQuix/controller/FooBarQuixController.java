package com.fabien.kata.fooBarQuix.controller;

import com.fabien.kata.fooBarQuix.service.FooBarQuixService;

import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("api/fooBarQuix")
public class FooBarQuixController {

    private final FooBarQuixService fooBarQuixService;
    private final JobLauncher jobLauncher;
    private final Job job;
    private final JobExplorer jobExplorer;

    public FooBarQuixController(FooBarQuixService fooBarQuixService, JobLauncher jobLauncher, Job job, JobExplorer jobExplorer) {
        this.fooBarQuixService = fooBarQuixService;
        this.jobLauncher = jobLauncher;
        this.job = job;
        this.jobExplorer = jobExplorer;
    }


    @GetMapping("/{num}")
    public String getFooBarQuix(@PathVariable int num){
        return fooBarQuixService.getFooBarQuix(num);
    }

    @GetMapping("/batch/start")
    public ResponseEntity<String> startJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt",System.currentTimeMillis())
                .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(job,jobParameters);

        return ResponseEntity.ok("Job Started with ID " + jobExecution.getId());
    }

    @GetMapping("/batch/status/{jobId}")
    public ResponseEntity<String> getJobStatus(@PathVariable Long jobId){
        JobExecution jobExecution = jobExplorer.getJobExecution(jobId);

        if(jobExecution == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job not Found");
        }

        return ResponseEntity.ok("Job status is : " + jobExecution.getStatus());
    }

    @GetMapping("/batch/result")
    public ResponseEntity<byte[]> getJobResult() throws IOException {
       File outputFile = new File("temp/test-files/output.csv");

        if (!outputFile.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        byte[] fileContent = Files.readAllBytes(outputFile.toPath());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=result.csv");
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");

        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);

    }

}
