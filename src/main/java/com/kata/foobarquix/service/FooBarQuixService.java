package com.kata.foobarquix.service;

import org.springframework.stereotype.Service;

@Service
public class FooBarQuixService {

    public static final String FOO = "FOO";
    public static final String BAR = "BAR";
    public static final String QUIX = "QUIX";

    public String transform(Integer number) {

        validateInput(number);
        StringBuilder result = new StringBuilder();

        if (number % 3 == 0) result.append(FOO);
        if (number % 5 == 0) result.append(BAR);

        String numStr = String.valueOf(number);
        numStr.chars()
              .forEach(c -> {
                  if (c == '3') result.append(FOO);
                  if (c == '5') result.append(BAR);
                  if (c == '7') result.append(QUIX);
              });

        return !result.isEmpty() ? result.toString() : numStr;
    }

    private void validateInput(Integer number) {

        if (number == null) {
            throw new IllegalArgumentException("Number Cannot be Null");
        }

        if (number < 0 || number > 100) {
            throw new IllegalArgumentException("Number must be between 1 and 100");
        }
    }
}
