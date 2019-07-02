package com.swedbank.hiring.utils;

import com.swedbank.hiring.entity.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class HouseListUtilsTest {

    @Test
    public void test() {
        Rule rule1 = new Rule(HousePositionReference.SAME, new Pair<String, String>("k", "v1"), new Pair<String, String>("k1", "v4"));
        Rule rule2 = new Rule(HousePositionReference.SAME, new Pair<String, String>("k", "v2"), new Pair<String, String>("k1", "v3"));
        List<Rule> rules = Arrays.asList(rule1, rule2);

        HouseLists houseLists = HouseListsBuilder.getInstance().build(rules, 2);

        Map<String, Set<String>> actualOutput = HouseListUtils.getAvailableValues(houseLists, rules);
        assertEquals(0, actualOutput.size());

        Rule rule3 = new Rule(HousePositionReference.SAME, new Pair<String, String>("k1", "v5"), null);
        rules = Arrays.asList(rule1, rule2, rule3);
        houseLists = HouseListsBuilder.getInstance().build(rules, 2);

        actualOutput = HouseListUtils.getAvailableValues(houseLists, rules);
        assertEquals(1, actualOutput.size());
    }
}
