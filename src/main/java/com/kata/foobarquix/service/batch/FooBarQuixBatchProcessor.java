package com.kata.foobarquix.service.batch;

import com.kata.foobarquix.model.Input;
import com.kata.foobarquix.model.TransformedResult;
import com.kata.foobarquix.service.FooBarQuixService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;

public class FooBarQuixBatchProcessor implements ItemProcessor<Input, TransformedResult> {

    private final FooBarQuixService fooBarQuixService;

    public FooBarQuixBatchProcessor(FooBarQuixService fooBarQuixService) {
        this.fooBarQuixService = fooBarQuixService;
    }

    @Override
    public TransformedResult process(@NonNull Input input) {
        return new TransformedResult(input.number(), fooBarQuixService.transform(input.number()));
    }
}
