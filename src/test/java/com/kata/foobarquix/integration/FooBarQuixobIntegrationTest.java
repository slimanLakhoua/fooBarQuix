package com.kata.foobarquix.integration;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class FooBarQuixobIntegrationTest {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job fooBarQuix;

    @Test
    void testJobExecution() throws Exception {
        JobExecution jobExecution = jobLauncher.run(fooBarQuix, new JobParameters());

        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
    }
}