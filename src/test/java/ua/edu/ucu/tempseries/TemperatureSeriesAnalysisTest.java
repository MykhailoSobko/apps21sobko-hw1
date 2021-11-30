package ua.edu.ucu.tempseries;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TemperatureSeriesAnalysisTest {
    private TemperatureSeriesAnalysis series = new TemperatureSeriesAnalysis(new double[] {1, 2, 3});

    @Test
    public void testAverageWithOneElementArray() {
        // setup input data and expected result
        double[] temperatureSeries = {-1.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = -1.0;

        // call tested method
        double actualResult = seriesAnalysis.average();

        // compare expected result with actual result
        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAverageWithEmptyArray() {
        double[] temperatureSeries = {};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        // expect exception here
        seriesAnalysis.average();
    }

    @Test
    public void testAverage() {
        double[] temperatureSeries = {3.0, -5.0, 1.0, 5.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = 1.0;

        double actualResult = seriesAnalysis.average();
        
        assertEquals(expResult, actualResult, 0.00001);        
    }

    @Test
    public void testDefaultConstructor() {
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis();
    }

    @Test
    public void testDeviation() {
        assertEquals(Math.sqrt((double) 2 / 3), series.deviation(), 0);
    }

    @Test
    public void testMin() {
        assertEquals(1, series.min(), 0);
    }

    @Test
    public void testMax() {
        assertEquals(3, series.max(), 0);
    }

    @Test
    public void testFindTempClosestToZero() {
        assertEquals(1, series.findTempClosestToZero(), 0);
    }

    @Test
    public void testFindTempClosestToValue() {
        assertEquals(2, series.findTempClosestToValue(2.1), 0);
    }

    @Test
    public void testFindTempsLessThen() {
        assertArrayEquals(new double[] {1, 2}, series.findTempsLessThen(3), 0);
    }

    @Test
    public void testFindTempsGreaterThen() {
        assertArrayEquals(new double[] {2, 3}, series.findTempsGreaterThen(2), 0);
    }

    @Test
    public void testSummaryStatistics() {
        TempSummaryStatistics summary = series.summaryStatistics();
        assertEquals(2, summary.getAvgTemp(), 0);
        assertEquals(Math.sqrt((double) 2 / 3), summary.getDevTemp(), 0);
        assertEquals(1, summary.getMinTemp(), 0);
        assertEquals(3, summary.getMaxTemp(), 0);

    }

    @Test
    public void testAddTemps() {
        series.addTemps(4, 5);
        assertEquals(3, series.average(), 0);
    }
}
