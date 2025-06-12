package com.fabien.kata.fooBarQuix.controller;

import com.fabien.kata.fooBarQuix.service.FooBarQuixService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;

import org.springframework.beans.factory.annotation.Value;
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

    @Value("${file.output}")
    private String outputFilePath;

    public FooBarQuixController(FooBarQuixService fooBarQuixService, JobLauncher jobLauncher, Job job, JobExplorer jobExplorer) {
        this.fooBarQuixService = fooBarQuixService;
        this.jobLauncher = jobLauncher;
        this.job = job;
        this.jobExplorer = jobExplorer;
    }

    @GetMapping("/{num}")
    @Operation(summary = "Converts a number into a character string")
    @ApiResponse(responseCode = "200", description = "Succes")
    @ApiResponse(responseCode = "400", description = "Failed or bad input")
    public String getFooBarQuix(@PathVariable int num){
        return fooBarQuixService.getFooBarQuix(num);
    }

    @GetMapping("/batch/start")
    @Operation(summary = "Start job, converts number in file name : input_numbers.txt")
    @ApiResponse(responseCode = "200", description = "Job started")
    public ResponseEntity<String> startJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt",System.currentTimeMillis())
                .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(job,jobParameters);

        return ResponseEntity.ok("Job Started with ID " + jobExecution.getId());
    }

    @GetMapping("/batch/status/{jobId}")
    @Operation(summary = "See status of job")
    @ApiResponse(responseCode = "200", description = "Return job status")
    @ApiResponse(responseCode = "404", description = "Job not found")
    @ApiResponse(responseCode = "400", description = "Bad request")
    public ResponseEntity<String> getJobStatus(@PathVariable Long jobId){
        JobExecution jobExecution = jobExplorer.getJobExecution(jobId);

        if(jobExecution == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job not Found");
        }

        return ResponseEntity.ok("Job status is : " + jobExecution.getStatus());
    }

    @GetMapping("/batch/result")
    @Operation(summary = "See status of job")
    @ApiResponse(responseCode = "200", description = "Return file output with number converted")
    public ResponseEntity<byte[]> getJobResult() throws IOException {
       File outputFile = new File(outputFilePath);

        if (!outputFile.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        byte[] fileContent = Files.readAllBytes(outputFile.toPath());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=output.csv");
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");

        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }
}
