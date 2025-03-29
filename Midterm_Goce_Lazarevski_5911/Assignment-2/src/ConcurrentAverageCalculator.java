import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

public class ConcurrentAverageCalculator {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide a list of file paths as command-line arguments.");
            return;
        }

        int[] results = {0, 0};
        Object lock = new Object();

        List<Runnable> tasks = new ArrayList<>();
        for (String filename : args) {
            tasks.add(new AverageCalculatorTask(filename, lock, results));
        }

        ExecutorService executor = Executors.newFixedThreadPool(args.length);
        for (Runnable task : tasks) {
            executor.submit(task);
        }

        executor.shutdown();

        try {
            long timeoutInMilliseconds = 1000;
            LocalDateTime startTime = LocalDateTime.now();

            while (true) {
                if (executor.isTerminated()) {
                    break;
                }

                long elapsedMilliseconds = Duration.between(startTime, LocalDateTime.now()).toMillis();
                if (elapsedMilliseconds > timeoutInMilliseconds) {
                    System.err.println("Some tasks did not complete within the timeout.");
                    executor.shutdownNow();
                    break;
                }

                Thread.sleep(10);
            }
        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted while waiting for executor shutdown.");
        }

        synchronized (lock) {
            if (results[1] > 0) {
                double average = (double) results[0] / results[1];
                average = Math.round(average * 100.0) / 100.0;
                System.out.println("Average: " + average);
            } else {
                System.out.println("No valid numbers to calculate an average.");
            }
        }
    }
}
