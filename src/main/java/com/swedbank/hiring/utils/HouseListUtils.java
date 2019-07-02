package com.swedbank.hiring.utils;

import com.swedbank.hiring.entity.HouseLists;
import com.swedbank.hiring.entity.Rule;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HouseListUtils {

    public static Map<String, Set<String>> getAvailableValues(HouseLists houseMapping, List<Rule> rules) {

        final Map<String, Set<String>> usedMappings = houseMapping.getAttributesValueMap();

        Map<String, Set<String>> availableValues = new HashMap<>();
        for (Rule rule : rules) {
            if (!usedMappings.get(rule.getPair1().getKey()).contains(rule.getPair1().getValue())) {
                if (!availableValues.containsKey(rule.getPair1().getKey())) {
                    availableValues.put(rule.getPair1().getKey(), new HashSet<>());
                }
                availableValues.get(rule.getPair1().getKey()).add(rule.getPair1().getValue());
            }
            if (rule.getPair2() != null && !usedMappings.get(rule.getPair2().getKey()).contains(rule.getPair2().getValue())) {
                if (!availableValues.containsKey(rule.getPair2().getKey())) {
                    availableValues.put(rule.getPair2().getKey(), new HashSet<>());
                }
                availableValues.get(rule.getPair2().getKey()).add(rule.getPair2().getValue());
            }
        }

        return availableValues;
    }
}
