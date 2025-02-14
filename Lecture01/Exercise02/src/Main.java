import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Path filePath = Paths.get("Lecture01/Exercise01/uniqueName_1739551789806.txt");
        try {
            List<String> lines = Files.readAllLines(filePath);
            System.out.println("Number of lines: " + lines.size());
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}