import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        Path filePath = Paths.get("Lecture01/Exercise05/123.bin");
        byte[] dataToWrite = {1, 2, 3, 4, 5};

        try {
            Files.write(filePath, dataToWrite);
            byte[] dataRead = Files.readAllBytes(filePath);
            for (byte b : dataRead) {
                System.out.print(b + " ");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}