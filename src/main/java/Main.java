import java.io.IOException;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws Exception {
//        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
//        System.out.println(engine.search("бизнес"));

        Socket client = null;
        while (true) {
            try (PdfSearchServer srv = new PdfSearchServer()) {
//                throw new IOException("client is null");
                client = srv.start();
                System.out.println("New connection has started on port: " + client.getPort());
                String word = srv.recieve();
                srv.sendSearchResult(word);
//                throw new IOException("client isn\'t closed");
                client.close();
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
                System.out.println(e.getLocalizedMessage());
                if (client != null && !client.isClosed()) {
                    client.close();
                }
                continue;
            }
        }
    }
}