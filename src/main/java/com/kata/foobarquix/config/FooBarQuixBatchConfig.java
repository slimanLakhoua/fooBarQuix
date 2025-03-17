package com.kata.foobarquix.config;

import com.kata.foobarquix.model.Input;
import com.kata.foobarquix.model.TransformedResult;
import com.kata.foobarquix.service.batch.FooBarQuixBatchProcessor;
import com.kata.foobarquix.service.FooBarQuixService;
import com.kata.foobarquix.service.batch.JobCompletionNotificationListener;
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
                .resource(new ClassPathResource("static/input-numbers.csv"))
                .delimited().names("number")
                .targetType(Input.class)
                .build();
    }

    @Bean
    public FooBarQuixBatchProcessor processor() {
        return new FooBarQuixBatchProcessor(fooBarQuixService);
    }

    @Bean
    public FlatFileItemWriter<TransformedResult> itemWriter() {
        return new FlatFileItemWriterBuilder<TransformedResult>()
                .name("itemWriter")
                .resource(new PathResource("temp/test-files/output.csv"))
                .lineAggregator(new PassThroughLineAggregator<>())
                .build();
    }

    @Bean
    public Job FooBarQuixJob(JobRepository jobRepository, Step step1, JobCompletionNotificationListener listener) {
        return new JobBuilder("FooBarQuixJob", jobRepository)
                .listener(listener)
                .start(step1)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                      FlatFileItemReader<Input> reader, FooBarQuixBatchProcessor processor, FlatFileItemWriter<TransformedResult> writer) {
        return new StepBuilder("step1", jobRepository)
                .<Input, TransformedResult>chunk(1, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

}
