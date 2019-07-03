package com.swedbank.hiring.utils;

import static com.swedbank.hiring.Main.HOUSE_COUNT;
import static com.swedbank.hiring.entity.Constants.POSITION;
import com.swedbank.hiring.entity.HouseLists;
import com.swedbank.hiring.entity.Rule;

import java.util.*;
import java.util.stream.IntStream;

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

        if(availableValues.containsKey(POSITION)) {
            Set<String> values = availableValues.get(POSITION);
            if (values.size() < HOUSE_COUNT) {
                final Set<String> usedPositions = !usedMappings.containsKey(POSITION) ? Collections.emptySet() : usedMappings.get(POSITION);
                IntStream.rangeClosed(1, HOUSE_COUNT).filter(i -> !usedPositions.contains(""+i)).forEach(i -> values.add("" + i));
            }
        }

        return availableValues;
    }
}
