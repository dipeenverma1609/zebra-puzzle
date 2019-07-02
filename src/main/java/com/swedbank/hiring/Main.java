package com.swedbank.hiring;

import com.swedbank.hiring.entity.HouseLists;
import com.swedbank.hiring.entity.HouseListsBuilder;
import com.swedbank.hiring.entity.Rule;
import com.swedbank.hiring.exception.InvalidInputException;
import com.swedbank.hiring.reader.RulesReader;
import com.swedbank.hiring.solver.Solver;
import com.swedbank.hiring.utils.HouseListUtils;
import com.swedbank.hiring.writer.HouseMappingsWriter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {

    public static final int HOUSE_COUNT = 5;

    public static void main(String[] args) {

        RulesReader rulesReader = new RulesReader();
        List<Rule> rules = new LinkedList<>();
        try {
            rules = rulesReader.read("input.csv");
        } catch (InvalidInputException e) {
            e.printStackTrace();
        }

        if (rules.isEmpty()) return;

        final HouseLists houseMapping = HouseListsBuilder.getInstance().build(rules, HOUSE_COUNT);
        final Map<String, Set<String>> availableValues = HouseListUtils.getAvailableValues(houseMapping, rules);

        final Solver solver = new Solver(rules);
        boolean result = solver.solve(houseMapping, availableValues);

        if (result) {
            System.out.println(houseMapping);
            HouseMappingsWriter.getInstance().write(houseMapping, "output.xml");
        } else {
            System.out.println("Solution Not Found");
        }
    }





}
