package com.swedbank.hiring.utils;

import com.swedbank.hiring.entity.HousePositionReference;
import com.swedbank.hiring.entity.Pair;
import com.swedbank.hiring.entity.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class RulesUtilTest {

    @Test
    public void testAttributeCount() {

        List<Pair<String, Integer>> actualOutput = RulesUtil.getAttributesCount(null);
        assertNull(actualOutput);

        actualOutput = RulesUtil.getAttributesCount(new ArrayList<Rule>());
        assertNull(actualOutput);

        Rule rule1 = new Rule(HousePositionReference.SAME, new Pair<String, String>("k", "v1"), null);
        Rule rule2 = new Rule(HousePositionReference.SAME, new Pair<String, String>("k", "v2"), new Pair<String, String>("k1", "v3"));

        actualOutput = RulesUtil.getAttributesCount(Arrays.asList(rule1, rule2));
        assertNotNull(actualOutput);

        Pair<String, Integer> pair1 = new Pair<>("k", 1);
        Pair<String, Integer> pair2 = new Pair<>("k1", 1);
        List<Pair<String, Integer>> expectedOutput = Arrays.asList(pair1, pair2);
        assertEquals(expectedOutput.size(), actualOutput.size());
    }
}
