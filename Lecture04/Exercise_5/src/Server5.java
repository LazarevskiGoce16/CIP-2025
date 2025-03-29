import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server5 {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        try {
            // create server socket
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);
            System.out.println("Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected from: " + clientSocket.getInetAddress());

                // handling client in a new thread
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing server socket: " + e.getMessage());
            }
        }
    }
    private static void handleClient(Socket clientSocket) {
        try {
            // setting up the communication streams
            ObjectInputStream fromClient = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream toClient = new ObjectOutputStream(clientSocket.getOutputStream());

            // reading array size
            int arraySize = fromClient.readInt();
            System.out.println("Receiving array of size: " + arraySize);

            // reading array elements
            int[] numbers = new int[arraySize];
            for (int i = 0; i < arraySize; i++) {
                numbers[i] = fromClient.readInt();
            }

            // printing received array
            System.out.println("Received array: [");
            for (int i = 0; i < arraySize; i++) {
                System.out.print(numbers[i]);
                if (i < arraySize - 1) {
                    System.out.println(", ");
                }
            }
            System.out.println("]");

            // finding the minimum element
            int min = findMinimum(numbers);
            System.out.println("Minimum element: " + min);

            // sending the result back to the client
            toClient.writeInt(min);
            toClient.flush();

            // closing resources
            fromClient.close();
            toClient.close();
            clientSocket.close();
            System.out.println("Client disconnected: " + clientSocket.getInetAddress());
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        }
    }

    private static int findMinimum (int[] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Array is empty");
        }

        int min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
        }

        return min;
    }
}
