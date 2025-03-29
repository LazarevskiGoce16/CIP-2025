import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class Server6 {
    private static final int PORT = 8080;
    private static final List<String> OPERATIONS = Arrays.asList("+", "-", "*", "/");

    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        try {
            // creating the server socket
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);
            System.out.println("Waiting for clients...");

            while (true) {
                // accepting the client connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected from: " + clientSocket.getInetAddress());

                // handling the client in a new thread
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
            // setting up communication streams
            PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // sending the list of supported operations to the client
            toClient.println(String.join(",", OPERATIONS));
            System.out.println("Sent operations list to client: " + String.join(",", OPERATIONS));

            while (true) {
                try {
                    // reading first number
                    String num1Str = fromClient.readLine();
                    if (num1Str == null) break; // Client disconnected
                    double num1 = Double.parseDouble(num1Str);

                    // reading second number
                    String num2Str = fromClient.readLine();
                    if (num2Str == null) break; // Client disconnected
                    double num2 = Double.parseDouble(num2Str);

                    // reading operator
                    String operator = fromClient.readLine();
                    if (operator == null) break; // Client disconnected

                    System.out.println("Received calculation request: " + num1 + " " + operator + " " + num2);

                    // performing calculations
                    double result;
                    switch (operator) {
                        case "+":
                            result = num1 + num2;
                            break;
                        case "-":
                            result = num1 - num2;
                            break;
                        case "*":
                            result = num1 * num2;
                            break;
                        case "/":
                            if (num2 == 0) {
                                toClient.println("ERROR: Division by zero");
                                continue;
                            }
                            result = num1 / num2;
                            break;
                        default:
                            toClient.println("ERROR: Unsupported operation");
                            continue;
                    }

                    // sending the result to the client
                    toClient.println(result);
                    System.out.println("Sent result to client: " + result);
                } catch (NumberFormatException e) {
                    toClient.println("ERROR: Invalid number format");
                } catch (Exception e) {
                    toClient.println("ERROR: " + e.getMessage());
                    System.err.println("Error processing client request: " + e.getMessage());
                }
            }

            // closing resources
            toClient.close();
            fromClient.close();
            clientSocket.close();
            System.out.println("Client disconnected: " + clientSocket.getInetAddress());
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        }
    }
}