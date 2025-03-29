import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class AverageCalculatorTask implements Runnable {
    private final String filename;
    private final Object lock;
    private final int[] results;

    public AverageCalculatorTask(String filename, Object lock, int[] results) {
        this.filename = filename;
        this.lock = lock;
        this.results = results;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    int number = Integer.parseInt(line.trim());
                    synchronized (lock) {
                        results[0] += number;
                        results[1]++;
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number format in file " + filename + ": " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file " + filename + ": " + e.getMessage());
        }
    }
}