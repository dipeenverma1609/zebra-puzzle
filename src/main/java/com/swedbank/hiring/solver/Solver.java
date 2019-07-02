package com.swedbank.hiring.solver;

import com.swedbank.hiring.entity.HouseLists;
import com.swedbank.hiring.entity.Pair;
import com.swedbank.hiring.entity.Rule;
import com.swedbank.hiring.utils.RulesUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.swedbank.hiring.entity.Constants.POSITION;

public class Solver {

    private final ValuesPermutation permutation = new ValuesPermutation();

    private List<Rule> rules;

    public Solver(List<Rule> rules) {
        this.rules = rules;
    }

    public boolean solve(final HouseLists houseMappings, final Map<String, Set<String>> availableValues) {

        final List<Pair<String, Integer>> attributesCount = RulesUtil.getAttributesCount(rules);
        final Optional<Pair<String, Integer>> attribute = attributesCount.stream().filter(attr -> availableValues.containsKey(attr.getKey())).findFirst();
        final String key = attribute.isPresent() ? attribute.get().getKey() : null;

        if (key == null) {

            List<Map<String, String>> mappingsWithPosition = houseMappings.stream().filter(v -> v.containsKey(POSITION))
                    .sorted((a,b) -> {
                        Integer i = Integer.parseInt(a.get(POSITION));
                        Integer j = Integer.parseInt(b.get(POSITION));
                        return i.compareTo(j);
                    }).collect(Collectors.toList());

            mappingsWithPosition.stream().forEach(m -> houseMappings.remove(m));

            for(Map<String, String> m : mappingsWithPosition) {
                houseMappings.add(Integer.parseInt(m.get(POSITION))-1, m);
            }

            int index = 0;
            while (index < houseMappings.size()) {
                Map<String, String> mapping = houseMappings.get(index);
                if (!mapping.containsKey(POSITION)) mapping.put(POSITION, ""+(index+1));
                index++;
            }

            boolean isValid = rules.stream().map(rule -> houseMappings.isValid(rule)).reduce(true, (a, b) -> a && b);
            if (isValid && houseMappings.isDataComplete(attributesCount.size())) {
                return true;
            }
            return false;

        } else {

            final Set<String> values = availableValues.get(key);
            availableValues.remove(key);

            final List<String[]> valuesPermutations = new LinkedList<>();
            permutation.generate(valuesPermutations, new String[0], values.toArray(new String[values.size()]));

            final List<Map<String, String>> mappingsMissingKey = houseMappings.getHousesWithoutKey(key);
            for (String[] valuesSet : valuesPermutations) {
                for (int i = 0; i < valuesSet.length; i++) {
                    mappingsMissingKey.get(i).put(key, valuesSet[i]);
                }

                boolean isValid = rules.stream().map(rule -> houseMappings.isValid(rule)).reduce(true, (a, b) -> a && b);
                if (isValid) {
                    if (houseMappings.isDataComplete(attributesCount.size())) {
                        return true;
                    } else if (solve(houseMappings, availableValues)) {
                        return true;
                    }
                }
            }

            availableValues.put(key, values);
            for (Map<String, String> item : mappingsMissingKey) item.remove(key);

            return false;
        }
    }




}
