package com.kata.foobarquix.controller;


import com.kata.foobarquix.service.FooBarQuixService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;

@RestController
@RequestMapping("/api")
public class FooBarQuixController {

    private final FooBarQuixService fooBarQuixService;

    private final JobLauncher jobLauncher;
    private final Job job;

    public FooBarQuixController(FooBarQuixService fooBarQuixService, JobLauncher jobLauncher, Job job) {
        this.fooBarQuixService = fooBarQuixService;
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    @GetMapping("/transform/{number}")
    public String transform(@PathVariable int number) {
        return fooBarQuixService.transform(number);
    }

    @GetMapping("/batch/start")
    public ResponseEntity<byte[]> startJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .toJobParameters();
        JobExecution jobExecution = jobLauncher.run(job, jobParameters);

        while (jobExecution.isRunning()) {
            Thread.sleep(1000);
        }

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
