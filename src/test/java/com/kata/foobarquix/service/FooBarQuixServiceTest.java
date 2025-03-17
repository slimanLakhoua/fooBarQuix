package com.kata.foobarquix.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class FooBarQuixServiceTest {

    private final FooBarQuixService service = new FooBarQuixService();


    @Test
    void validateInput_throwsException_whenNumberIsNull() {
        assertThrows(IllegalArgumentException.class, () -> service.transform(null), "Number Cannot be Null");
    }

    @Test
    void validateInput_throwsException_whenNumberIsLessThanZero() {
        assertThrows(IllegalArgumentException.class, () -> service.transform(-1), "Number must be between 1 and 100");
    }

    @Test
    void validateInput_throwsException_whenNumberIsGreaterThan100() {
        assertThrows(IllegalArgumentException.class, () -> service.transform(101), "Number must be between 1 and 100");
    }

    @Test
    void transform_returnsNumberAsString_whenNumberIsNotDivisibleBy3Or5AndDoesNotContain3Or5Or7() {
        assertEquals("1", service.transform(1));
    }

    @Test
    void transform_returnsFOO_whenNumberIsDivisibleBy3AndContains3() {
        assertEquals("FOOFOO", service.transform(3));
    }

    @Test
    void transform_returnsBAR_whenNumberIsDivisibleBy5AndContains5() {
        assertEquals("BARBAR", service.transform(5));
    }

    @Test
    void transform_returnsQUIX_whenNumberContains7() {
        assertEquals("QUIX", service.transform(7));
    }

    @Test
    void transform_returnsQUIX_whenNumberContains9() {
        assertEquals("FOO", service.transform(9));
    }

    @Test
    void transform_returnsFOOBAR_whenNumberIsDivisibleBy3And5() {
        assertEquals("FOOBAR", service.transform(51));
    }

    @Test
    void transform_returnsFOOFOO_whenNumberContains3And5() {
        assertEquals("BARFOO", service.transform(53));
    }

    @Test
    void transform_returnsBARBAR_whenNumberContains3TwiceAndVDivisibleBy3() {
        assertEquals("FOOFOOFOO", service.transform(33));
    }

    @Test
    void transform_returnsFOOBARQUIX_whenNumberIsDivisibleBy3And5AndContains5() {
        assertEquals("FOOBARBAR", service.transform(15));
    }

}