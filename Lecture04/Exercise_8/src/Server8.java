import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server8 {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        try {
            // creating server socket
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started at port " + PORT);
            System.out.println("Waiting for clients...");

            while (true) {
                try {

                    // accepting client connection
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected from: " + clientSocket.getInetAddress());

                    // handling client in a new thread
                    new Thread(() -> handleClient(clientSocket)).start();
                } catch (IOException e) {
                    System.err.println("Error accepting client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        } finally {
            try {
                // closing the server socket
                if (serverSocket != null) {
                    serverSocket.close();
                    System.out.println("Server socket closed.");
                }
            } catch (IOException e) {
                System.err.println("Error closing server socket: " + e.getMessage());
            }
        }
    }

    private static void handleClient(Socket clientSocket) {
        try {
            // setting up communication streams
            ObjectInputStream fromClient = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream toClient = new ObjectOutputStream(clientSocket.getOutputStream());

            // reading array size
            int arraySize = fromClient.readInt();
            System.out.println("Receiving array size of: " + arraySize);

            // reading array elements
            int[] numbers = new int[arraySize];
            for (int i = 0; i < arraySize; i++) {
                numbers[i] = fromClient.readInt();
            }

            // printing received array
            System.out.print("Received array: [");
            for (int i = 0; i < arraySize; i++) {
                System.out.print(numbers[i]);
                if (i < arraySize - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");

            // sorting the array
            Arrays.sort(numbers);
            System.out.println("Array sorted successfully");

            // sending the sorted array to the client
            toClient.writeInt(arraySize);
            for (int num : numbers) {
                toClient.writeInt(num);
            }
            toClient.flush();
            System.out.println("Sorted array sent to client");

            // closing the resources
            fromClient.close();
            toClient.close();
            clientSocket.close();
            System.out.println("Client disconnected: " + clientSocket.getInetAddress());
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        }
    }
}