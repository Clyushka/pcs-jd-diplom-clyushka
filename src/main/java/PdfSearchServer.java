import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class PdfSearchServer implements AutoCloseable {
    private BooleanSearchEngine engine;
    private ServerSocket serverSocket;
    private BufferedReader in;
    private PrintWriter out;

    public PdfSearchServer() throws IOException {
        serverSocket = new ServerSocket(8989);
        engine = new BooleanSearchEngine(new File("pdfs"));
    }

    public Socket start() throws IOException {
        Socket clientSocket = serverSocket.accept();
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        return clientSocket;
    }

    public String recieve() throws IOException {
        return in.readLine();
    }

    public void send(String message) {
        out.println(message);
    }

    public void sendSearchResult(String word) throws JsonProcessingException {
        List<PageEntry> pageEntries = engine.search(word);
        ObjectMapper mapper = new ObjectMapper();
        //mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String result = mapper.writeValueAsString(pageEntries);
        out.println(result);
    }

    public void close() throws IOException {
        serverSocket.close();
    }
}
