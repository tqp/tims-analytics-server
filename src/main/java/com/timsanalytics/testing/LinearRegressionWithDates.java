package com.timsanalytics.testing;

import com.timsanalytics.common.beans.KeyValueDouble;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LinearRegressionWithDates {

    private static Double predictForValue(List<KeyValueDouble> dataList, int predictForDependentVariable) {
        List<Double> x = dataList.stream().map(KeyValueDouble::getValue).collect(Collectors.toList());
        List<Long> y = dataList.stream().map(item -> {
            LocalDate localDate = LocalDate.parse(item.getKey());
            Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
            return instant.toEpochMilli();
        }).collect(Collectors.toList());

        if (x.size() != y.size())
            throw new IllegalStateException("Must have equal X and Y data points");

        Integer numberOfDataValues = x.size();

        List<Double> xSquared = x
                .stream()
                .map(position -> Math.pow(position, 2))
                .collect(Collectors.toList());

        List<Double> xMultipliedByY = IntStream.range(0, numberOfDataValues)
                .mapToDouble(i -> x.get(i) * y.get(i))
                .boxed()
                .collect(Collectors.toList());

        Double xSummed = x
                .stream()
                .reduce((prev, next) -> prev + next)
                .get();

        Long ySummed = y
                .stream()
                .reduce((prev, next) -> prev + next)
                .get();

        Double sumOfXSquared = xSquared
                .stream()
                .reduce((prev, next) -> prev + next)
                .get();

        Double sumOfXMultipliedByY = xMultipliedByY
                .stream()
                .reduce((prev, next) -> prev + next)
                .get();

        Double slopeNominator = numberOfDataValues * sumOfXMultipliedByY - ySummed * xSummed;
        Double slopeDenominator = numberOfDataValues * sumOfXSquared - Math.pow(xSummed, 2);
        Double slope = slopeNominator / slopeDenominator;

        double interceptNominator = ySummed - slope * xSummed;
        double interceptDenominator = numberOfDataValues;
        Double intercept = interceptNominator / interceptDenominator;

        return (slope * predictForDependentVariable) + intercept;
    }

    public static void main(String[] args) {
        List<KeyValueDouble> data = getDateData();
        Double predictedValue = predictForValue(data, 100000);
        long predictedValueLong = Math.round(predictedValue);
        System.out.println(LocalDate.ofInstant(Instant.ofEpochMilli(predictedValueLong), ZoneId.systemDefault()));
    }

    public static List<KeyValueDouble> getDateData() {
        List<KeyValueDouble> dataList = new ArrayList<>();
        dataList.add(new KeyValueDouble("2019-02-24", 313.0));
        dataList.add(new KeyValueDouble("2019-03-05", 646.0));
        dataList.add(new KeyValueDouble("2019-03-16", 1008.0));
        dataList.add(new KeyValueDouble("2019-03-28", 1363.0));
        dataList.add(new KeyValueDouble("2019-04-07", 1711.0));
        dataList.add(new KeyValueDouble("2019-04-13", 2082.0));
        dataList.add(new KeyValueDouble("2019-04-22", 2444.0));
        return dataList;
    }

    public static void convertLocalDateToEpoch() {
        LocalDate localDate = LocalDate.parse("2019-11-15");

        //LocalDate to epoch days
        long numberOfDays = localDate.toEpochDay();
        System.out.println(numberOfDays);

        //LocalDate to epoch seconds
        long timeInSeconds = localDate.toEpochSecond(LocalTime.NOON, ZoneOffset.MIN);
        System.out.println(timeInSeconds);

        //LocalDate to epoch milliseconds
        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        long timeInMillis = instant.toEpochMilli();
        System.out.println(timeInMillis);

        instant = localDate.atTime(LocalTime.MIDNIGHT).atZone(ZoneId.systemDefault()).toInstant();
        timeInMillis = instant.toEpochMilli();
        System.out.println(timeInMillis);
    }

}
