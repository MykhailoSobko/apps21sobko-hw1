package ua.edu.ucu.tempseries;

import java.util.Arrays;

public class TemperatureSeriesAnalysis {
    private static final double ABSOLUTE_ZERO = -273;
    private static final double MIN_DIFFERENCE = 0.0000001;
    private double [] temps;
    private int size;
    private int capacity;

    public TemperatureSeriesAnalysis() {
        size = 0;
        capacity = 0;
        temps = new double[] {};
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        size = temperatureSeries.length;
        capacity = temperatureSeries.length;
        temps = Arrays.copyOf(temperatureSeries, capacity);
    }

    public double sum() {
        if (size == 0) {
            throw new IllegalArgumentException();
        }
        double sum = 0;
        for (int i = 0; i < size; i++) {
            sum = sum + temps[i];
        }
        return sum;
    }

    public double average() {
        return sum() / size;
    }

    public double deviation() {
        if (size == 0) {
            throw new IllegalArgumentException();
        }
        double deviation = 0;
        double mean = average();
        for (int i = 0; i < size; i++) {
            deviation += (temps[i] - mean) * (temps[i] - mean);
        }
        return Math.sqrt(deviation / size);
    }

    public double min() {
        return findTempClosestToValue(ABSOLUTE_ZERO);
    }

    public double max() {
        return findTempClosestToValue(Double.MAX_VALUE);
    }

    public double findTempClosestToZero() {
        return findTempClosestToValue(0);
    }

    public double findTempClosestToValue(double tempValue) {
        if (size == 0) {
            throw new IllegalArgumentException();
        }
        double minDiff = Double.MAX_VALUE;
        double tempClosest = 0;

        for (int i = 0; i < size; i++) {
            double currTemp = temps[i];
            double currDiff = Math.abs(currTemp - tempValue);

            if (currDiff < minDiff) {
                minDiff = currDiff;
                tempClosest = currTemp;
            }
            else if (Math.abs(currDiff - minDiff) < MIN_DIFFERENCE) {
                tempClosest = Math.max(tempClosest, currTemp);
            }
        }
        return tempClosest;
    }

    public double[] findTempsLessThan(double tempValue) {
        int count = 0;
        double[] lessValues = new double[size];

        for (int i = 0; i < capacity; i++) {
            double currTemp = temps[i];
            if (currTemp < tempValue) {
                lessValues[count] = currTemp;
                count++;
            }
        }
        return Arrays.copyOfRange(lessValues, 0, count);
    }

    public double[] findTempsGreaterThan(double tempValue) {
        int count = 0;
        double[] greaterValues = new double[size];

        for (int i = 0; i < capacity; i++) {
            double currTemp = temps[i];
            if (currTemp >= tempValue) {
                greaterValues[count] = currTemp;
                count++;
            }
        }
        return Arrays.copyOf(greaterValues, count);
    }

    public TempSummaryStatistics summaryStatistics() {
        if (size == 0) {
            throw new IllegalArgumentException();
        }
        return new TempSummaryStatistics(this);
    }

    public double addTemps(double... newTemps) {
        for (double newTemp : newTemps) {
            if (size == capacity) {
                if (capacity == 0) {
                    capacity++;
                }
                else {
                    capacity *= 2;
                }
                temps = Arrays.copyOf(temps, capacity);
            }
            temps[size] = newTemp;
            size++;
        }
        return sum();
    }
}
