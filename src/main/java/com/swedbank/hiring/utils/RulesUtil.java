package com.swedbank.hiring.utils;

import com.swedbank.hiring.entity.Pair;
import com.swedbank.hiring.entity.Rule;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByValue;

public class RulesUtil {

    public static List<Pair<String, Integer>> getAttributesCount(List<Rule> rules) {
        Map<String, Integer> countMap = new HashMap<>();

        if (rules == null || rules.isEmpty()) return null;

        for ( Rule rule : rules) {
            if (rule.isSame() && rule.getPair2()!=null) {
                Integer value = countMap.get(rule.getPair1().getKey());
                countMap.put(rule.getPair1().getKey(), (value == null ? 1 : value + 1));

                if (rule.getPair2().getKey() != null) {
                    value = countMap.get(rule.getPair2().getKey());
                    countMap.put(rule.getPair2().getKey(), (value == null ? 1 : value + 1));
                }
            }
        }

        countMap = countMap.entrySet().stream().sorted((comparingByValue(Comparator.reverseOrder())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                        LinkedHashMap::new));

        return countMap.entrySet().stream().map(e -> new Pair<String, Integer>(e.getKey(), e.getValue())).collect(Collectors.toList());
    }
}
