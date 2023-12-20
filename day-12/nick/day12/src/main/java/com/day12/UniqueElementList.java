package com.day12;

import java.util.List;
import java.util.ArrayList;

public class UniqueElementList {

    private ArrayList<List<Integer>> mainList;

    public UniqueElementList() {
        this.mainList = new ArrayList<>();
    }

    public boolean add(List<Integer> newList) {
        // Check if any sublist already contains elements from the new list
        for (List<Integer> existingList : mainList) {
            if (existingList.containsAll(newList)) {
                return false;
            }
        }

        // If no duplicates, add the new list
        return mainList.add(newList);
    }

    public List<List<Integer>> getMainList() {
        return new ArrayList<>(mainList);
    }
}
