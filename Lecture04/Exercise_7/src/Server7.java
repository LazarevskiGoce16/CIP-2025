import java.net.*;
import java.io.*;
import java.util.Arrays;

class Server7 {
    private static final int PORT = 8080;

    public static void main(String[] argv) {
        ServerSocket serverSocket = null;

        try {
            // creating the server socket
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);
            System.out.println("Waiting for clients...");

            while (true) {
                try {
                    // accepting client connection
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected from: " + clientSocket.getInetAddress());

                    // handling the client in a new thread
                    new Thread(() -> handleClient(clientSocket)).start();
                } catch (IOException e) {
                    System.err.println("Error accepting client: " + e.getMessage());
                }
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
            // setting up communication streams
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

            // sending sorted array back to client
            toClient.writeInt(arraySize);
            for (int i = 0; i < arraySize; i++) {
                toClient.writeInt(numbers[i]);
            }
            toClient.flush();
            System.out.println("Sorted array sent to client");

            // closing resources
            fromClient.close();
            toClient.close();
            clientSocket.close();
            System.out.println("Client disconnected: " + clientSocket.getInetAddress());

        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        }
    }
}