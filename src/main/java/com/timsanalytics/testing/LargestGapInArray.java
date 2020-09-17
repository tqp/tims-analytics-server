package com.timsanalytics.testing;


import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LargestGapInArray {

    public static void main(String[] args) {
        List<LocalDate> list = new ArrayList<>();
        list.add(LocalDate.of(2020, 1, 12));
        list.add(LocalDate.of(2020, 2, 11));
        list.add(LocalDate.of(2020, 3, 10));
        list.add(LocalDate.of(2020, 4, 9));
        list.add(LocalDate.of(2020, 5, 8));
        list.add(LocalDate.of(2020, 6, 7));
        list.add(LocalDate.of(2020, 7, 6));
        list.add(LocalDate.of(2020, 8, 5));
        list.add(LocalDate.of(2020, 9, 4));
        list.add(LocalDate.of(2020, 10, 3));
        list.add(LocalDate.of(2020, 11, 2));
        list.add(LocalDate.of(2020, 12, 1));
        System.out.println("Largest gap is: " + getLargestGapInArray(list));
    }

    private static int getLargestGapInArray(List<LocalDate> dateArray) {
        List<LocalDate> dateArraySorted = dateArray.stream()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());

        int maxGap = 0;
        for (int i = 1; i <= dateArraySorted.size() - 1; i++) {
            Period diff = Period.between(dateArraySorted.get(i - 1), dateArraySorted.get(i));
//            System.out.println(dateArraySorted.get(i - 1) + " - " + dateArraySorted.get(i) + " = " + diff.getDays());
            maxGap = Math.max(diff.getDays(), maxGap);
        }
        return maxGap;
    }

    public static void PrintObject(String title, Object object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            System.out.println(title + ":\n" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
