package com.webservices.crypto.domain;

/**
 * RunningStatistics class is used to calculate
 * mean and standard deviation of running stream of numbers
 * This class use's Welford's algorithm for estimation.
 * The algorithm keeps member variables in the class, and performs the following
 * update when seeing a new variable number:
 */
public class RunningStatistics {

    private RunningStatistics() {}

    private static RunningStatistics statisticsInstance = null;

    /**
     * Get StatisticsCalculation Singleton Instance
     * @return singleton instance
     */
    public static RunningStatistics getInstance() {
        if(statisticsInstance==null){
            synchronized(RunningStatistics.class){
                if(statisticsInstance == null) {
                    statisticsInstance = new RunningStatistics();
                }
            }
        }
        return statisticsInstance;
    }

    private long count;

    private double mean, sq;

    /**
     * Push value to the running stream
     * @param number
     */
    public synchronized void push(double number) {
        count++;
        final double delta = number - mean;
        mean = mean + delta / count;
        sq += delta * (number - mean);
    }


    /**
     * Get the current mean
     * @return mean
     */
    public synchronized double getMean() {
        return mean;
    }


    /**
     * Get the current variance
     * @return variance
     */
    public synchronized double getVariance() {
        return sq / count;
    }

    /**
     * Get the standard deviation
     * @return standard deviation
     */
    public synchronized double getStdDev() {
        return Math.sqrt(getVariance());
    }

    /**
     * Get the current count value
     * @return
     */
    public synchronized long getCount() {
        return count;
    }

    /**
     * Reset the values to initial state
     */
    public synchronized void reset() {
        count = 0;
        mean = 0;
        sq = 0;
    }

}