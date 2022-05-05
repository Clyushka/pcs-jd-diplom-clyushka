import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BooleanSearchEngine implements SearchEngine {
    private Map<String, List<PageEntry>> wordsInPdfDir = new HashMap<>();

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        List<File> pdfFiles = Arrays.stream(pdfsDir.listFiles())
                .filter(x -> x.getName().endsWith(".pdf"))
                .collect(Collectors.toUnmodifiableList());

        for (File pdfFile : pdfFiles) {
            PdfDocument pdfDoc = new PdfDocument(new PdfReader(pdfFile));
            for (int page = 1; page <= pdfDoc.getNumberOfPages(); page++) {
                Map<String, Integer> wordsMap = new HashMap<>();
                String[] words = PdfTextExtractor.getTextFromPage(
                        pdfDoc.getPage(page)).split("\\P{IsAlphabetic}+");

                for (String word : words) {
                    if (word.isEmpty()) {
                        continue;
                    }
                    wordsMap.put(word.toLowerCase(), wordsMap.getOrDefault(word, 0) + 1);
                }

                //поиск по странице документа завершен, добавляем PageEntry
                int thisPage = page;
                for (String word : wordsMap.keySet()) {
                    if (wordsInPdfDir.containsKey(word)) {
                        wordsInPdfDir.get(word).add(new PageEntry(pdfFile.getName(), thisPage, wordsMap.get(word)));
                    } else {
                        wordsInPdfDir.put(word, new ArrayList<>() {{
                            add(new PageEntry(pdfFile.getName(), thisPage, wordsMap.get(word)));
                        }});
                    }
                }
            }
        }
    }

    @Override
    public List<PageEntry> search(String word) {
        List<PageEntry> result = wordsInPdfDir.get(word);
        Collections.sort(result);
        return result;
    }
}
