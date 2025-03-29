import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client6 {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) {
        Socket clientSocket = null;

        try {
            // connecting to server
            System.out.println("Connecting to server at " + SERVER_IP + ":" + SERVER_PORT + "...");
            clientSocket = new Socket(SERVER_IP, SERVER_PORT);
            System.out.println("Connected successfully!");

            // setting up communication streams
            PrintWriter toServer = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            Scanner scanner = new Scanner(System.in);

            // receiving available operations from server
            String operationsStr = fromServer.readLine();
            String[] operations = operationsStr.split(",");

            System.out.println("\nAvailable operations:");
            for (String op : operations) {
                System.out.println("  " + op);
            }

            while (true) {
                try {
                    System.out.println("\n--- New Calculation ---");

                    // getting the first number
                    System.out.print("Enter first number (or 'exit' to quit): ");
                    String input = scanner.nextLine();
                    if (input.equalsIgnoreCase("exit")) {
                        break;
                    }
                    double num1 = Double.parseDouble(input);

                    // getting the second number
                    System.out.print("Enter second number: ");
                    double num2 = Double.parseDouble(scanner.nextLine());

                    // getting the operator
                    System.out.print("Enter operator (" + operationsStr + "): ");
                    String operator = scanner.nextLine();

                    // sending data to server
                    toServer.println(num1);
                    toServer.println(num2);
                    toServer.println(operator);

                    // receiving and display result
                    String result = fromServer.readLine();
                    if (result.startsWith("ERROR")) {
                        System.out.println("Error: " + result.substring(7));
                    } else {
                        System.out.println("\nResult: " + num1 + " " + operator + " " + num2 + " = " + result);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number format. Please try again.");
                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                    break;
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Cannot find server at " + SERVER_IP);
        } catch (IOException e) {
            System.err.println("Error communicating with server: " + e.getMessage());
        } finally {
            // closing resources
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                    System.out.println("Connection closed.");
                }
            } catch (IOException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
