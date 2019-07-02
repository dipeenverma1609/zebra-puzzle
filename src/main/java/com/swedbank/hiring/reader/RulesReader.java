package com.swedbank.hiring.reader;

import com.swedbank.hiring.entity.HousePositionReference;
import com.swedbank.hiring.entity.Pair;
import com.swedbank.hiring.entity.Rule;
import com.swedbank.hiring.exception.InvalidInputException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class RulesReader {

    public List<Rule> read(final String filename) throws InvalidInputException {
        final List<String> lines = new LinkedList<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNext()) {
                lines.add(scanner.nextLine());
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }

        List<Rule> rules = new LinkedList<>();
        for (String str : lines) {
            if (str == null) {
                throw new InvalidInputException("The input is empty");
            }


            String[] values = str.split(",");
            if (values.length % 2 == 0 || (values.length != 5 && values.length != 3)) {
                throw new InvalidInputException(String.format("Illegal number of inputs :: %s", str));
            }

            HousePositionReference direction = HousePositionReference.getValue(values[0]);
            if (direction == null) {
                throw new InvalidInputException(String.format("Unknown house reference :: %s", str));
            }

            int i = 1;
            List<Pair<String, String>> pairsList = new ArrayList();
            while (i < values.length) {
                String key = values[i];
                String value = values[i+1];

                if (key == null || key.isEmpty()) throw new InvalidInputException(String.format("Empty key :: %s", str));
                if (value == null || value.isEmpty()) throw new InvalidInputException(String.format("Empty value of a key :: %s", str));

                Pair<String, String> pair = new Pair(key, value);
                pairsList.add(pair);
                i+=2;
            }

            rules.add(createRule(direction, pairsList));

        }
        return rules;
    }

    private Rule createRule(HousePositionReference direction, List<Pair<String, String>> pairsList) throws InvalidInputException{
        final Pair<String, String> pair1 = pairsList.get(0);
        final Pair<String, String> pair2 = pairsList.size() > 1 ? pairsList.get(1) : null;

        switch (direction) {
            case SAME:
                if (pair2 != null) {
                    if (pair1.contains(pair2.getKey()) && !pair1.getValue().equals(pair2.getValue())) {
                        throw new InvalidInputException(String.format("key repeated %s & %s", pair1, pair2));
                    }
                }
                break;
            case NEXT_TO:
            case TO_THE_LEFT_OF:
                if (pair2 == null) {
                    throw new InvalidInputException(String.format("%s - must have house reference %s", direction.name(), pair1));
                }
                if (pair1.contains(pair2.getKey()) && pair1.getValue().equals(pair2.getValue())) {
                    throw new InvalidInputException(String.format("key repeated %s & %s", pair1, pair2));
                }
                break;
        }

        return new Rule(direction, pair1, pair2);
    }

}
