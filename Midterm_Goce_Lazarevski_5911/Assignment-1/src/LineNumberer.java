import java.io.*;

public class LineNumberer {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java LineNumberer <inputFilePath> <outputFilePath>");
            return;
        }

        String inputFilePath = args[0];
        String outputFilePath = args[1];

        try (BufferedReader reader = new BufferedReader(new FileReader(new File(inputFilePath)));
             FileWriter writer = new FileWriter(new File(outputFilePath))) {

            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                writer.write(lineNumber + ": " + line + "\n");
                lineNumber++;
            }

            System.out.println("File has been numbered successfully!");
        } catch (FileNotFoundException e) {
            System.err.println("Error: Input file not found.");
            return;
        } catch (IOException e) {
            System.err.println("Error: An I/O error occurred while processing the files.");
            return;
        }

        System.out.println("\nContents of the Input File:");
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(inputFilePath)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error: Unable to read the input file.");
        }

        System.out.println("\nContents of the Numbered File:");
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(outputFilePath)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error: Unable to read the numbered file.");
        }
    }
}
