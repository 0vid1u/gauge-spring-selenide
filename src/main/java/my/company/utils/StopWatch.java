package my.company.utils;

public final class StopWatch {

    /* Private Instance Variables */
    /**
     * Stores the start time when an object of the StopWatch class is initialized.
     */
    private long startTime;

    public StopWatch() {
        this.startTime = System.nanoTime();
    }

    public long getElapsedTime() {
        long endTime = System.nanoTime();
        return (endTime - startTime) / (1000000);
    }

    public void reset() {
        this.startTime = System.nanoTime();
    }
}