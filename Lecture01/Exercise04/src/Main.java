import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Path directoryPath = Paths.get("Lecture01/Exercise01/");
        try {
            Stream<Path> stream = Files.list(directoryPath);
            if(stream.findFirst().isPresent()) {
                System.out.println("Directory is NOT empty.");
            } else {
                System.out.println("Directory is empty!");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}