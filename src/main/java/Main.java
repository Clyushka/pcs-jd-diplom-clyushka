import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
//        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
//        System.out.println(engine.search("бизнес"));

        while (true) {
            try (PdfSearchServer srv = new PdfSearchServer()) {
                int clientPort = srv.start();
                System.out.println("New connection has started on port: " + clientPort);
                srv.send("Write a word to search for: ");
                String word = srv.recieve();
                srv.sendSearchResult(word);
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
                System.out.println(e.getLocalizedMessage());
                System.exit(0);
            }
        }
    }
}