package com.kata.foobarquix.service.batch;

import com.kata.foobarquix.model.Input;
import com.kata.foobarquix.model.TransformedResult;
import com.kata.foobarquix.service.FooBarQuixService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class FooBarQuixBatchProcessorTest {

    @Mock
    private FooBarQuixService fooBarQuixService;

    @InjectMocks
    private FooBarQuixBatchProcessor fooBarQuixBatchProcessor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void processTransformsPositiveNumber() {
        // Given
        Input input = new Input(3);
        String transformed = "FooFoo";
        when(fooBarQuixService.transform(input.number())).thenReturn(transformed);

        // When
        TransformedResult result = fooBarQuixBatchProcessor.process(input);

        // Then
        assertEquals(new TransformedResult(input.number(), transformed), result);
    }

    @Test
    void processTransformsZero() {
        // Given
        Input input = new Input(0);
        String transformed = "0";
        when(fooBarQuixService.transform(input.number())).thenReturn(transformed);
        // When
        TransformedResult result = fooBarQuixBatchProcessor.process(input);
        // Then
        assertEquals(new TransformedResult(input.number(), transformed), result);
    }

    @Test
    void processThrowsExceptionForNullInput() {
        // Given
        Input input = null;

        // When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> fooBarQuixBatchProcessor.process(input));
        // Then
        assertEquals("Input cannot be null", exception.getMessage());
    }

}