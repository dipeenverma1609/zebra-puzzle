package com.swedbank.hiring.solver;

import com.swedbank.hiring.entity.HouseLists;
import com.swedbank.hiring.entity.Pair;
import com.swedbank.hiring.entity.Rule;

import java.util.Map;

import static com.swedbank.hiring.Main.HOUSE_COUNT;
import static com.swedbank.hiring.entity.Constants.POSITION;

public class RulesValidaters {

    public static boolean isValid(HouseLists houseMappings, Rule rule) {
        boolean isValid = false;
        switch (rule.getDirection()) {
            case SAME:
                for (Map<String, String> mapping : houseMappings) {
                    isValid = isValidForSame(rule, mapping);
                    if (!isValid) return false;
                }
                break;
            case NEXT_TO:
                Map<String, String> mapping = houseMappings.getMappingForPair(rule.getPair1());
                isValid = isValidForNextTo(mapping, rule.getPair2(), houseMappings);

                if (!isValid) {
                    mapping = houseMappings.getMappingForPair(rule.getPair2());
                    isValid = isValidforPreviousTo(mapping, rule.getPair1(), houseMappings);
                }
                break;
            case TO_THE_LEFT_OF:
                mapping = houseMappings.getMappingForPair(rule.getPair1());
                isValid = isValidforPreviousTo(mapping, rule.getPair2(), houseMappings);

                if (!isValid) {
                    mapping = houseMappings.getMappingForPair(rule.getPair2());
                    isValid = isValidForNextTo(mapping, rule.getPair1(), houseMappings);
                }
                break;
        }
        return isValid;
    }

    public static boolean isValidForSame(final Rule rule, Map<String, String> mapping) {
        if (mapping.containsKey(rule.getPair1().getKey()) && mapping.get(rule.getPair1().getKey()).equals(rule.getPair1().getValue())) {
            if (rule.getPair2() != null && mapping.containsKey(rule.getPair2().getKey()) && mapping.get(rule.getPair2().getKey()).equals(rule.getPair2().getValue())) {
                return true;
            } else {
                if (rule.getPair2() != null && mapping.containsKey(rule.getPair2().getKey())) {
                    return false;
                }
            }
        } else {
            if (rule.getPair2()!= null && mapping.containsKey(rule.getPair2().getKey()) && mapping.get(rule.getPair2().getKey()).equals(rule.getPair2().getValue())) {
                if (mapping.containsKey(rule.getPair1().getKey())) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isValidForNextTo(Map<String, String> mapping, Pair<String, String> nextPair, HouseLists houseMappings) {
        if (mapping != null && mapping.containsKey(POSITION)) {

            int position = Integer.parseInt(mapping.get(POSITION));
            if (position + 1 > HOUSE_COUNT) {
                return false;
            }
            Map<String, String> nextItem = houseMappings.getMappingForPair(new Pair<>(POSITION, (position + 1) + ""));
            if (nextItem != null) {
                if (nextItem.containsKey(nextPair.getKey()) && nextItem.get(nextPair.getKey()).equals(nextPair.getValue())) {
                    return true;
                } else {
                    if (nextItem.containsKey(nextPair)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public static boolean isValidforPreviousTo(Map<String, String> mapping, Pair<String, String> prevPair, HouseLists houseMappings) {
        if (mapping != null && mapping.containsKey(POSITION)) {
            int position = Integer.parseInt(mapping.get(POSITION));
            if (position - 1 < 1) {
                return false;
            }
            Map<String, String> previousItem = houseMappings.getMappingForPair(new Pair<>(POSITION, (position - 1) + ""));
            if (previousItem != null) {
                if (previousItem.containsKey(prevPair.getKey()) && previousItem.get(prevPair.getKey()).equals(prevPair.getValue())) {
                    return true;
                } else {
                    if (previousItem.containsKey(prevPair)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
