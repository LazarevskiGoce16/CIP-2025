import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        URI uri = URI.create("https://uacs.edu.mk/home/home");

        try {
            URL url = uri.toURL();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("download_uacs.html"));

            String readLine;
            while ((readLine = bufferedReader.readLine()) != null) {
                bufferedWriter.write(readLine);
                bufferedWriter.newLine();
            }

            System.out.println("The file is downloaded from URL.");
            bufferedReader.close();
            bufferedWriter.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}