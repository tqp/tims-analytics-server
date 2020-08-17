package com.timsanalytics.testing;

import com.timsanalytics.common.beans.KeyValueIntInt;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.google.common.primitives.Ints.asList;

public class LinearRegressionWithIntegers {
//    private static final List<Integer> x = asList(2, 3, 5, 7, 9, 11, 14); // Consecutive hours developer codes
//    private static final List<Integer> y = asList(4, 5, 7, 10, 15, 20, 40); // Number of bugs produced

    private static Double predictForValue(List<KeyValueIntInt> dataList, int predictForDependentVariable) {
        List<Integer> x = dataList.stream().map(KeyValueIntInt::getKey).collect(Collectors.toList());
        List<Integer> y = dataList.stream().map(KeyValueIntInt::getValue).collect(Collectors.toList());

        if (x.size() != y.size())
            throw new IllegalStateException("Must have equal X and Y data points");

        Integer numberOfDataValues = x.size();

        List<Double> xSquared = x
                .stream()
                .map(position -> Math.pow(position, 2))
                .collect(Collectors.toList());

        List<Integer> xMultipliedByY = IntStream.range(0, numberOfDataValues)
                .map(i -> x.get(i) * y.get(i))
                .boxed()
                .collect(Collectors.toList());

        Integer xSummed = x
                .stream()
                .reduce((prev, next) -> prev + next)
                .get();

        Integer ySummed = y
                .stream()
                .reduce((prev, next) -> prev + next)
                .get();

        Double sumOfXSquared = xSquared
                .stream()
                .reduce((prev, next) -> prev + next)
                .get();

        Integer sumOfXMultipliedByY = xMultipliedByY
                .stream()
                .reduce((prev, next) -> prev + next)
                .get();

        int slopeNominator = numberOfDataValues * sumOfXMultipliedByY - ySummed * xSummed;
        Double slopeDenominator = numberOfDataValues * sumOfXSquared - Math.pow(xSummed, 2);
        Double slope = slopeNominator / slopeDenominator;

        double interceptNominator = ySummed - slope * xSummed;
        double interceptDenominator = numberOfDataValues;
        Double intercept = interceptNominator / interceptDenominator;

        return (slope * predictForDependentVariable) + intercept;
    }

    public static void main(String[] args) {
        List<KeyValueIntInt> data = getData();
        System.out.println(predictForValue(data, 8));
    }

    public static List<KeyValueIntInt> getData() {
        List<KeyValueIntInt> dataList = new ArrayList<>();
        dataList.add(new KeyValueIntInt(1, 2));
        dataList.add(new KeyValueIntInt(2, 4));
        dataList.add(new KeyValueIntInt(3, 6));
        dataList.add(new KeyValueIntInt(4, 8));
        dataList.add(new KeyValueIntInt(5, 10));
        dataList.add(new KeyValueIntInt(6, 12));
        dataList.add(new KeyValueIntInt(7, 14));
        return dataList;
    }
}
