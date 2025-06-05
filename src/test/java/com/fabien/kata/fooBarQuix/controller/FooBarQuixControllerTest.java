package com.fabien.kata.fooBarQuix.controller;

import com.fabien.kata.fooBarQuix.service.FooBarQuixService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FooBarQuixControllerTest {
    private FooBarQuixService fooBarQuixService;
    private JobLauncher jobLauncher;
    private Job job;
    private JobExplorer jobExplorer;
    private FooBarQuixController controller;

    @BeforeEach
    void setUp() {
        fooBarQuixService = mock(FooBarQuixService.class);
        jobLauncher = mock(JobLauncher.class);
        job = mock(Job.class);
        jobExplorer = mock(JobExplorer.class);
        controller = new FooBarQuixController(fooBarQuixService, jobLauncher, job, jobExplorer);
    }

    @Test
    void getFooBarQuix_with3() {
        int input = 3;
        String expected = "FOOFOO";

        when(fooBarQuixService.getFooBarQuix(input)).thenReturn(expected);

        String result = controller.getFooBarQuix(input);

        assertEquals(expected, result);
        verify(fooBarQuixService).getFooBarQuix(input);
    }

    @Test
    void getFooBarQuix_with5() {
        int input = 5;
        String expected = "BARBAR";

        when(fooBarQuixService.getFooBarQuix(input)).thenReturn(expected);

        String result = controller.getFooBarQuix(input);

        assertEquals(expected, result);
        verify(fooBarQuixService).getFooBarQuix(input);
    }

    @Test
    void getFooBarQuix_with7() {
        int input = 7;
        String expected = "QUIX";

        when(fooBarQuixService.getFooBarQuix(input)).thenReturn(expected);

        String result = controller.getFooBarQuix(input);

        assertEquals(expected, result);
        verify(fooBarQuixService).getFooBarQuix(input);
    }

    @Test
    void startJob_returnJobId() throws Exception {
        JobExecution jobExecution = mock(JobExecution.class);
        when(jobExecution.getId()).thenReturn(1L);
        when(jobLauncher.run(eq(job), any(JobParameters.class))).thenReturn(jobExecution);

        ResponseEntity<String> response = controller.startJob();

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Job Started with ID 1"));
        verify(jobLauncher).run(eq(job), any(JobParameters.class));
    }

    @Test
    void getJobStatus_returnStatus() {
        Long jobId = 1L;
        JobExecution jobExecution = mock(JobExecution.class);
        when(jobExplorer.getJobExecution(jobId)).thenReturn(jobExecution);
        when(jobExecution.getStatus()).thenReturn(BatchStatus.COMPLETED);

        ResponseEntity<String> response = controller.getJobStatus(jobId);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().matches("Job status is : COMPLETED"));
    }

    @Test
    void getJobStatus_returnNotFoundIfJobNotExists() {
        Long jobId = 999L;
        when(jobExplorer.getJobExecution(jobId)).thenReturn(null);

        ResponseEntity<String> response = controller.getJobStatus(jobId);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Job not Found", response.getBody());
    }
}
