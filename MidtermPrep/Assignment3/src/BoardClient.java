import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class BoardClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8080);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            Scanner scanner = new Scanner(System.in);

            System.out.println(bufferedReader.readLine());
            while (true) {
                String commandInput = scanner.nextLine();
                printWriter.println(commandInput);

                if (commandInput.equalsIgnoreCase("BYE")) break;
                String response;
                while ((response = bufferedReader.readLine()) != null && !response.equalsIgnoreCase("Enter command: ")) {
                    System.out.println(response);
                }
                if ((response != null && response.equalsIgnoreCase("Enter command: "))) {
                    System.out.println(response);
                }
            }
        } catch (IOException e) {
            System.out.println("Server is not available!");
        }
    }
}
