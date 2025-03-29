import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client5 {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) {
        Socket clientSocket = null;

        try {
            // connecting to the server
            System.out.println("Connecting to server at " + SERVER_IP + ":" + SERVER_PORT + "...");
            clientSocket = new Socket(SERVER_IP, SERVER_PORT);
            System.out.println("Connected successfully!");

            // setting up communication streams
            ObjectOutputStream toServer = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream fromServer = new ObjectInputStream(clientSocket.getInputStream());
            Scanner scanner = new Scanner(System.in);

            // getting the array size from user
            System.out.print("Enter the size of the array: ");
            int size = scanner.nextInt();

            // creating and filling the array
            int[] numbers = new int[size];
            System.out.println("Enter " + size + " integers:");

            for (int i = 0; i < size; i++) {
                System.out.print("Element " + (i + 1) + ": ");
                numbers[i] = scanner.nextInt();
            }

            // displaying the array
            System.out.print("Array to send: [");
            for (int i = 0; i < size; i++) {
                System.out.print(numbers[i]);
                if (i < size - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");

            // sending the array size to server
            toServer.writeInt(size);

            // sending the array elements to server
            for (int i = 0; i < size; i++) {
                toServer.writeInt(numbers[i]);
            }
            toServer.flush();

            System.out.println("Array sent to server. Waiting for response...");

            // receiving the minimum element from server
            int minimum = fromServer.readInt();
            System.out.println("\nResult from server:");
            System.out.println("Minimum element in the array: " + minimum);
        } catch (UnknownHostException e) {
            System.err.println("Cannot find the server at " + SERVER_IP);
        } catch (IOException e) {
            System.err.println("Error communicating with server: " + e.getMessage());
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                    System.out.println("Server socket closed.");
                }
            } catch (IOException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
