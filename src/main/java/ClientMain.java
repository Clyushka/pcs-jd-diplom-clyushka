import java.io.IOException;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {
        int serverPort = 8989;
        String ipAddress = "127.0.0.1";

        try {
            Client client = new Client(ipAddress, serverPort);
            Scanner consoleScanner = new Scanner(System.in);
            client.send(consoleScanner.nextLine());
            System.out.println(client.recieve());
            client.close();
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }

    }
}