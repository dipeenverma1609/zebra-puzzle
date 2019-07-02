package com.swedbank.hiring.entity;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class HouseListsBuilderTest {

    @Test
    public void testBuilder() {
        Rule rule1 = new Rule(HousePositionReference.SAME, new Pair<>("k", "v1"), new Pair<>("k1", "v4"));
        Rule rule2 = new Rule(HousePositionReference.SAME, new Pair<>("k", "v2"), new Pair<>("k1", "v3"));
        List<Rule> rules = Arrays.asList(rule1, rule2);
        int housesCount = 2;

        HouseLists actualOutput = HouseListsBuilder.getInstance().build(rules, housesCount);

        assertNotNull(actualOutput);
        assertEquals(housesCount, actualOutput.size());
    }

    @Test
    public void testBuilderIncompleteInputs() {
        Rule rule1 = new Rule(HousePositionReference.SAME, new Pair<>("k", "v1"), null);
        Rule rule2 = new Rule(HousePositionReference.SAME, new Pair<>("k", "v2"), new Pair<>("k1", "v3"));
        List<Rule> rules = Arrays.asList(rule1, rule2);
        int housesCount = 2;

        try {
            HouseListsBuilder.getInstance().build(rules, housesCount);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }
}
