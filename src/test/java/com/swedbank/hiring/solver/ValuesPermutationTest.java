package com.swedbank.hiring.solver;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class ValuesPermutationTest {

    @Test
    public void testGenerate() {

        String[] input = {"a", "b", "c"};
        final List<String[]> output = new LinkedList<>();

        ValuesPermutation testClass = new ValuesPermutation();
        testClass.generate(output, new String[0], input);

        assertNotNull(output);
        assertNotEquals(0, output.size());
        assertEquals(6, output.size());
    }

    @Test
    public void testGenerateEmptyInput() {

        final List<String[]> output = new LinkedList<>();

        ValuesPermutation testClass = new ValuesPermutation();
        testClass.generate(output, new String[0], new String[0]);

        assertNotNull(output);
        assertEquals(1, output.size());
        assertEquals(0, output.get(0).length);
    }
}
