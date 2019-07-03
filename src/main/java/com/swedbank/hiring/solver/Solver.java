package com.swedbank.hiring.solver;

import com.swedbank.hiring.entity.HouseLists;
import com.swedbank.hiring.entity.Pair;
import com.swedbank.hiring.entity.Rule;
import com.swedbank.hiring.exception.ValidationFailedException;
import com.swedbank.hiring.utils.RulesUtil;

import java.util.*;
import java.util.stream.Collectors;

import static com.swedbank.hiring.entity.Constants.POSITION;

public class Solver {

    private final ValuesPermutation permutation = new ValuesPermutation();

    private List<Rule> rules;

    public Solver(List<Rule> rules) {
        this.rules = rules;
    }

    public boolean solve(final HouseLists houseMappings, final Map<String, Set<String>> availableValues) {

        System.out.println(houseMappings);
        final List<Pair<String, Integer>> attributesCount = RulesUtil.getAttributesCount(rules);
        final Optional<Pair<String, Integer>> attribute = attributesCount.stream().filter(attr -> availableValues.containsKey(attr.getKey())).findFirst();
        final String key = attribute.isPresent() ? attribute.get().getKey() : null;

        final Set<String> values = availableValues.get(key);
        availableValues.remove(key);

        final List<String[]> valuesPermutations = new LinkedList<>();
        permutation.generate(valuesPermutations, new String[0], values.toArray(new String[values.size()]));

        final List<Map<String, String>> mappingsMissingKey = houseMappings.getHousesWithoutKey(key);
        for (String[] valuesSet : valuesPermutations) {
            for (int i = 0; i < valuesSet.length; i++) {
                mappingsMissingKey.get(i).put(key, valuesSet[i]);
            }

            try {
                boolean isValid = houseMappings.isValid(rules);
                if (isValid) {
                    if (houseMappings.isDataComplete(attributesCount.size())) {
                        return true;
                    } else if (solve(houseMappings, availableValues)) {
                        return true;
                    }
                }
            } catch (ValidationFailedException e){
                if (!e.failedKeys().contains(key)) {
                    availableValues.put(key, values);
                    for (Map<String, String> item : mappingsMissingKey) item.remove(key);
                    return false;
                }
            }
        }

        availableValues.put(key, values);
        for (Map<String, String> item : mappingsMissingKey) item.remove(key);

        return false;
    }




}
