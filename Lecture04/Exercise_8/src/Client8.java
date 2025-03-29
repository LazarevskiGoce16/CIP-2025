import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client8 {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 8080;

    private static final String INPUT_FILE = "Lecture04/Exercise_8/src/input.txt";
    private static final String OUTPUT_FILE = "Lecture04/Exercise_8/src/output.txt";
    public static void main(String[] args) {
        Socket clientSocket = null;

        try {
            // connecting to server
            System.out.println("Connecting to server at " + SERVER_IP + ":" + SERVER_PORT + "...");
            clientSocket = new Socket(SERVER_IP, SERVER_PORT);
            System.out.println("Connected successfully");

            // setting up communication streams
            ObjectOutputStream toServer = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream fromServer = new ObjectInputStream(clientSocket.getInputStream());

            // reading array from input file
            System.out.println("Reading array from file: " + INPUT_FILE);
            int[] numbers = readArrayFromFile(INPUT_FILE);
            
            if (numbers == null || numbers.length == 0) {
                System.err.println("Error: No valid array data found in the input file.");
                return;
            }

            int size = numbers.length;

            // displaying the original array
            System.out.print("\nOriginal array: [");
            for (int i = 0; i < size; i++) {
                System.out.print(numbers[i]);
                if (i < size - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");

            // sending the array size to the server
            toServer.writeInt(size);

            // sending array elements to the server
            for (int number : numbers) {
                toServer.writeInt(number);
            }
            toServer.flush();

            System.out.println("Array sent to server for sorting...");

            // receiving the sorted array
            int sortedSize = fromServer.readInt();
            int[] sortedArray = new int[sortedSize];

            for (int i = 0; i < sortedSize; i++) {
                sortedArray[i] = fromServer.readInt();
            }

            // displaying the sorted array
            System.out.print("\nSorted array: [");
            for (int i = 0; i < sortedSize; i++) {
                System.out.print(sortedArray[i]);
                if (i < sortedSize - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");

            // writing the sorted array to output file
            writeArrayToFile(OUTPUT_FILE, sortedArray);
            System.out.println("Sorted array written to file: " + OUTPUT_FILE);
        } catch (UnknownHostException e) {
            System.err.println("Cannot find server at " + SERVER_IP);
        } catch (IOException e) {
            System.err.println("Error communicating with server: " + e.getMessage());
        }
    }
    
    public static int[] readArrayFromFile(String filename) {
        try {
            // file communication stream
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            // reading array size
            String sizeLine = reader.readLine();
            int size = Integer.parseInt(sizeLine.trim());

            if (size <= 0) {
                reader.close();
                throw new IllegalArgumentException("Invalid array size: " + size);
            }

            // reading array elements
            int[] array = new int[size];
            for (int i = 0; i < size; i++) {
                String line = reader.readLine();
                if (line == null) {
                    reader.close();
                    throw new IOException("Not enough elements in the file");
                }
                array[i] = Integer.parseInt(line.trim());
            }

            reader.close();
            return array;
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading from file: " + e.getMessage());
            return null;
        }
    }

    public static void writeArrayToFile(String filename, int[] array) {
        try {
            // file communication stream
            PrintWriter writer = new PrintWriter(new FileWriter(filename));

            // writing the array size
            writer.println(array.length);

            // writing the array elements
            writer.println("Original array size: " + array.length);
            writer.println("Original array: ");
            for (int i = 0; i < array.length; i++) {
                writer.print(array[i]);
                if (i < array.length - 1) {
                    writer.print(", ");
                }
            }
            writer.println();

            writer.println("Sorted array: ");
            for (int i = 0; i < array.length; i++) {
                writer.print(array[i]);
                if (i < array.length - 1) {
                    writer.print(", ");
                }
            }

            writer.close();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
