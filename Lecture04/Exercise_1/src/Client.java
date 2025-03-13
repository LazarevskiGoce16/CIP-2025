import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) {
        Socket clientSocket = null;
        PrintWriter outputToServer = null;
        BufferedReader inputFromServer = null;
        BufferedReader userInput = null;

        try {
            // connecting to the server
            System.out.println("Connecting to server at: " + SERVER_ADDRESS + ":" + SERVER_PORT + "...");
            clientSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            System.out.println("Connected successfully");

            // setting up communication streams
            outputToServer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
            inputFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            userInput = new BufferedReader(new InputStreamReader(System.in));

            // sending initial greeting to the server
            outputToServer.println("Hello server!");
            String serverResponse = inputFromServer.readLine();
            System.out.println("Server says: " + serverResponse);

            // getting first number from the user
            System.out.println("Please enter the first number: ");
            String number1 = userInput.readLine();
            outputToServer.println(number1);

            // getting second number from the user
            System.out.println("Please enter the second number: ");
            String number2 = userInput.readLine();
            outputToServer.println(number2);

            // receiving and displaying the result
            String result = inputFromServer.readLine();
            System.out.println("\nCalculation Result:");
            System.out.println("-------------------");
            System.out.println(number1 + " + " + number2 + " = " + result);
        } catch (UnknownHostException e) {
            System.err.println("Cannot find the server at " + SERVER_ADDRESS);
        } catch (IOException e) {
            System.err.println("Error in communication with server: " + e.getMessage());
        } finally {
            // cleaning up resources
            try {
                if (outputToServer != null) outputToServer.close();
                if (inputFromServer != null) inputFromServer.close();
                if (userInput != null) userInput.close();
                if (clientSocket != null) clientSocket.close();
                System.out.println("\nConnection closed.");
            } catch (IOException e) {
                System.err.println("Error while closing resources: " + e.getMessage());
            }
        }
    }
}