package com.timsanalytics.main.thisApp.services;

import com.timsanalytics.common.beans.KeyValue;
import com.timsanalytics.common.beans.KeyValueDouble;
import com.timsanalytics.main.thisApp.dao.FuelTrackerDao;
import com.timsanalytics.utils.PrintObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class FuelTrackerService {
    private final FuelTrackerDao fuelTrackerDao;
    private final PrintObjectService printObjectService;

    @Autowired
    public FuelTrackerService(FuelTrackerDao fuelTrackerDao, PrintObjectService printObjectService) {
        this.fuelTrackerDao = fuelTrackerDao;
        this.printObjectService = printObjectService;
    }

    public KeyValueDouble getLongestTimeBetweenFills() {
        return new KeyValueDouble("result", this.fuelTrackerDao.getLongestTimeBetweenFills());
    }

    public KeyValueDouble getLongestDistanceBetweenFills() {
        return new KeyValueDouble("result", this.fuelTrackerDao.getLongestDistanceBetweenFills());
    }

    public KeyValue getEstimated1kDate() {
        List<KeyValueDouble> odometerData = this.fuelTrackerDao.getOdometerData();

        // x = Odometer Reading
        List<Double> x = odometerData.stream().map(KeyValueDouble::getValue).collect(Collectors.toList());

        // y = Date of Fill. Format: YYYY-MM-dd HH:mm:ss
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<Long> y = odometerData.stream().map(item -> {
            LocalDateTime localDateTime = LocalDateTime.parse(item.getKey(), inputFormat);
            Instant instant = localDateTime.atZone(ZoneOffset.UTC).toInstant();
            return instant.toEpochMilli();
        }).collect(Collectors.toList());

        Double predictedValue = predictForValue(x, y, 100000);
        long predictedValueLong = Math.round(predictedValue);
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        return new KeyValue("result", outputFormat.format(LocalDate.ofInstant(Instant.ofEpochMilli(predictedValueLong), ZoneId.systemDefault())));
    }

    public List<KeyValueDouble> getOdometerData() {
        return this.fuelTrackerDao.getOdometerData();
    }

    public List<KeyValueDouble> getMpgData() {
        return this.fuelTrackerDao.getMpgData();
    }

    private static Double predictForValue(List<Double> x, List<Long> y, int predictForDependentVariable) {
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
}
