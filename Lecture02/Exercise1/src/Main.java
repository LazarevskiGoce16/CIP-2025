import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a hostname: ");

        String hostname = scanner.nextLine();
        try {
            InetAddress address = InetAddress.getByName(hostname);
            System.out.println("Host address: " + address.getHostAddress());
            System.out.println("Canonical hostname: " + address.getCanonicalHostName());
            System.out.println("Is it reachable in 5 seconds: " + address.isReachable(5000));

            InetAddress[] addresses = InetAddress.getAllByName(hostname);
            for (InetAddress address1 : addresses) {
                System.out.println(address1);
            }
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
