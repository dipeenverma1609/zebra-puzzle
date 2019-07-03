package com.swedbank.hiring.exception;

import com.swedbank.hiring.entity.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValidationFailedException extends Exception {

    private List<String> failedKeys = new ArrayList<>();

    public ValidationFailedException(String... keys) {
        failedKeys.addAll(Arrays.asList(keys));
    }

    public List<String> failedKeys() {
        return failedKeys;
    }
}
