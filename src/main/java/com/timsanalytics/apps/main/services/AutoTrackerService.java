package com.timsanalytics.apps.main.services;

import com.timsanalytics.apps.main.beans.FuelActivity;
import com.timsanalytics.apps.main.dao.AutoTrackerDao;
import com.timsanalytics.apps.realityTracker.beans.Contestant;
import com.timsanalytics.common.beans.KeyValue;
import com.timsanalytics.common.beans.KeyValueDouble;
import com.timsanalytics.common.beans.ServerSidePaginationRequest;
import com.timsanalytics.common.beans.ServerSidePaginationResponse;
import com.timsanalytics.utils.PrintObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AutoTrackerService {
    private final AutoTrackerDao autoTrackerDao;

    @Autowired
    public AutoTrackerService(AutoTrackerDao autoTrackerDao) {
        this.autoTrackerDao = autoTrackerDao;
    }

    // Fuel Activity

    public ServerSidePaginationResponse<FuelActivity> getFuelActivityList_SSP(ServerSidePaginationRequest serverSidePaginationRequest) {
        ServerSidePaginationResponse<FuelActivity> serverSidePaginationResponse = new ServerSidePaginationResponse<FuelActivity>();
        serverSidePaginationResponse.setServerSidePaginationRequest(serverSidePaginationRequest);
        List<FuelActivity> fuelActivityList = this.autoTrackerDao.getFuelActivityList_SSP(serverSidePaginationRequest);
        serverSidePaginationResponse.setData(fuelActivityList);
        serverSidePaginationResponse.setLoadedRecords(fuelActivityList.size());
        serverSidePaginationResponse.setTotalRecords(this.autoTrackerDao.getFuelActivityList_SSP_TotalRecords(serverSidePaginationRequest));
        return serverSidePaginationResponse;
    }

    public FuelActivity getFuelActivityDetail(String fuelActivityGuid) {
        return this.autoTrackerDao.getFuelActivityDetail(fuelActivityGuid);
    }

    // Dashboard

    public KeyValueDouble getLongestTimeBetweenFills() {
        return new KeyValueDouble("result", this.autoTrackerDao.getLongestTimeBetweenFills());
    }

    public KeyValueDouble getLongestDistanceBetweenFills() {
        return new KeyValueDouble("result", this.autoTrackerDao.getLongestDistanceBetweenFills());
    }

    public KeyValue getEstimated1kDate() {
        List<KeyValueDouble> odometerData = this.autoTrackerDao.getOdometerData();

        // x = Odometer Reading
        List<Double> x = odometerData.stream().map(KeyValueDouble::getValue).collect(Collectors.toList());

        // y = Date of Fill. Format: YYYY-MM-dd HH:mm:ss
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<Long> y = odometerData.stream().map(item -> {
            LocalDateTime localDateTime = LocalDateTime.parse(item.getKey(), inputFormat);
            Instant instant = localDateTime.atZone(ZoneOffset.UTC).toInstant();
            return instant.toEpochMilli();
        }).collect(Collectors.toList());

        Double predictedValue = predictForValue(x, y);
        long predictedValueLong = Math.round(predictedValue);
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        return new KeyValue("result", outputFormat.format(LocalDate.ofInstant(Instant.ofEpochMilli(predictedValueLong), ZoneId.systemDefault())));
    }

    public List<KeyValueDouble> getOdometerData() {
        return this.autoTrackerDao.getOdometerData();
    }

    public List<KeyValueDouble> getMpgData() {
        return this.autoTrackerDao.getMpgData();
    }

    private static Double predictForValue(List<Double> x, List<Long> y) {
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
                .reduce(Double::sum)
                .orElse(null);

        Long ySummed = y
                .stream()
                .reduce(Long::sum)
                .orElse(null);

        Double sumOfXSquared = xSquared
                .stream()
                .reduce(Double::sum)
                .orElse(null);

        Double sumOfXMultipliedByY = xMultipliedByY
                .stream()
                .reduce(Double::sum)
                .orElse(null);

        Double slopeNominator = numberOfDataValues * sumOfXMultipliedByY - ySummed * xSummed;
        Double slopeDenominator = numberOfDataValues * sumOfXSquared - Math.pow(xSummed, 2);
        double slope = slopeNominator / slopeDenominator;

        double interceptNominator = ySummed - slope * xSummed;
        double interceptDenominator = numberOfDataValues;
        double intercept = interceptNominator / interceptDenominator;

        return (slope * 100000) + intercept;
    }
}
