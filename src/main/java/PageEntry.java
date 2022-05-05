import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PageEntry implements Comparable<PageEntry> {
    private final String pdfName;
    private final int page;
    private final int count;

    public PageEntry() {
        this.pdfName = "";
        this.page = 0;
        this.count = 0;
    }

    @JsonCreator
    public PageEntry(
            @JsonProperty("pdfName") String pdfName,
            @JsonProperty("page") int page,
            @JsonProperty("count") int count) {
        this.pdfName = (pdfName != null && !pdfName.isEmpty()) ? pdfName : "";
        this.page = (page >= 0) ? page : 0;
        this.count = (count >= 0) ? count : 0;
    }

    @JsonGetter("pdfName")
    public String getPdfName() {
        return pdfName;
    }

    @JsonGetter("page")
    public int getPage() {
        return page;
    }

    @JsonGetter("count")
    public int getCount() {
        return count;
    }

    @Override
    public int compareTo(PageEntry pageEntry) {
        int diff = pageEntry.count - this.count;

        //counts
        if (diff != 0) {
            return diff;
        }

        // if (this.count == pageEntry.count)
        diff = this.pdfName.compareTo(pageEntry.pdfName);
        if (diff != 0) {//pdfNames
            return diff;
        }

        //if this.pdfName == pageEntry.pdfName
        return this.page - pageEntry.page;
    }

    @Override
    public String toString() {
        return String.format("PageEntry{pdf=%s, page=%d, count=%d}",
                pdfName, page, count);
    }
}
