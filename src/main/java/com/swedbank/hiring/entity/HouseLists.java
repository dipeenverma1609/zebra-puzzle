package com.swedbank.hiring.entity;

import com.swedbank.hiring.solver.RulesValidaters;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class HouseLists extends LinkedList<Map<String, String>> {

    public Map<String, Set<String>> getAttributesValueMap() {
        return this.stream().flatMap(m -> m.entrySet().stream())
                .collect(Collectors.groupingBy(e -> e.getKey(), Collectors.mapping(e -> e.getValue(), Collectors.toSet())));
    }

    public boolean isDataComplete(int attrCount) {
        return !this.stream().filter(map -> map.size() != attrCount).findAny().isPresent();
    }

    public List<Map<String, String>> getHousesWithoutKey(final String key) {
        return this.stream().filter(map -> !map.containsKey(key)).collect(Collectors.toList());
    }

    public List<Map<String, String>> getHousesWithKey(final String key) {
        return this.stream().filter(map -> map.containsKey(key)).collect(Collectors.toList());
    }

    public boolean isValid(Rule rule) {
        return RulesValidaters.isValid(this, rule);
    }

    public Map<String, String> getMappingForPair(Pair<String, String> pair) {
        Optional<Map<String, String>> entry = this.stream().filter(map -> map.containsKey(pair.getKey()) && map.get(pair.getKey()).equals(pair.getValue())).findFirst();
        return entry.isPresent() ?  entry.get() : null;
    }

    public Set<String> attributesSet() {
        return this.stream().flatMap(m -> m.keySet().stream()).collect(Collectors.toSet());
    }

}
