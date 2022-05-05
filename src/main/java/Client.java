import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class Client {
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;

    public Client() throws IOException {
        client = new Socket();
        startStreams();
    }

    public Client(int port) throws IOException {
        client = new Socket("localhost", port);
        startStreams();
    }

    public Client(String host, int port) throws IOException {
        client = new Socket(host, port);
        startStreams();
    }

    private void startStreams() throws IOException {
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(), true);
    }

    public String recieve() throws IOException {
        return in.readLine();
    }

    public void send(String message) {
        out.println(message);
    }

    public void close() throws IOException {
        client.close();
    }
}