package com.swedbank.hiring.entity;

import com.swedbank.hiring.utils.RulesUtil;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HouseListsBuilder {
    private static HouseListsBuilder ourInstance = new HouseListsBuilder();

    public static HouseListsBuilder getInstance() {
        return ourInstance;
    }

    private HouseListsBuilder() {
    }

    public HouseLists build(List<Rule> rules, int houseCount) {
        {

            final List<Pair<String, Integer>> attributesCount = RulesUtil.getAttributesCount(rules);
            final String key = attributesCount.get(0).getKey();

            final Map<Pair<String, String>, List<Pair<String, String>>> initialMap = new LinkedHashMap<>();
            for (Rule rule : rules) {
                if (rule.isSame() && rule.getPair2()!=null && rule.containsKey(key)) {
                    final Pair<String, String> keyPair = rule.getPair1().contains(key) ? rule.getPair1() : rule.getPair2();
                    final Pair<String, String> relatedPair = rule.getPair1().contains(key) ? rule.getPair2() : rule.getPair1();

                    List<Pair<String, String>> p = initialMap.get(keyPair);
                    if (p == null) {
                        p = new LinkedList<>();
                        initialMap.put(keyPair, p);
                    }
                    p.add(relatedPair);
                }
            }

            if (initialMap.size() != houseCount) {
                throw new IllegalArgumentException("Input values are not complete");
            }

            final HouseLists houseLists = new HouseLists();
            initialMap.entrySet().stream().forEach(o -> {
                final Map<String, String> houseMap = new LinkedHashMap<>();

                houseMap.put(o.getKey().getKey(), o.getKey().getValue());
                o.getValue().forEach(p -> houseMap.put(p.getKey(), p.getValue()));

                houseLists.add(houseMap);
            });

            return houseLists;
        }
    }
}
