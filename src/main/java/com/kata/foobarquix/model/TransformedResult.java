package com.kata.foobarquix.model;

public record TransformedResult(int number, String result) {
    @Override
    public String toString() {
        return number + "       " + result;
    }
}
