package com.swedbank.hiring.solver;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ValuesPermutation {

    public void generate(List<String[]> result, String[] startArray, String[] endArray) {
        if (endArray.length <= 1) {
            final List<String> currentResult = new LinkedList<>();
            Collections.addAll(currentResult, startArray);
            Collections.addAll(currentResult, endArray);
            result.add(currentResult.toArray(new String[currentResult.size()]));
        } else {
            for (int i = 0; i < endArray.length; i++) {
                final List<String> newEndArray = new LinkedList<String>();
                newEndArray.addAll(Arrays.asList(endArray).subList(0, i));
                newEndArray.addAll(Arrays.asList(endArray).subList(i + 1, endArray.length));

                final List<String> newBeginArray = new LinkedList<String>();
                Collections.addAll(newBeginArray, startArray);
                newBeginArray.add(endArray[i]);

                generate(result, newBeginArray.toArray(new String[newBeginArray.size()]), newEndArray.toArray(new String[newEndArray.size()]));
            }
        }
    }
}
