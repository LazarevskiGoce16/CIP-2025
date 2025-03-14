import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderTask extends Thread {
    private String filename;

    public FileReaderTask(String filename) {
        this.filename = filename;
    }

    @Override
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}