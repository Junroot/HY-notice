package hynotice.postcollector.presentation.postcollector.httpconnection;

import java.util.Collections;
import java.util.List;
import org.jsoup.Connection;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;

public class CustomConnection extends HttpConnection {

    private final List<Document> documents;
    private int currentIndex = 0;

    public CustomConnection(final Document document) {
        this(Collections.singletonList(document));
    }

    public CustomConnection(final List<Document> document) {
        this.documents = document;
    }

    @Override
    public Connection requestBody(final String body) {
        if (body.contains("pageNum=1")) {
            currentIndex = 0;
            return this;
        }
        currentIndex = 1;
        return this;
    }

    @Override
    public Document post() {
        return documents.get(currentIndex);
    }

    @Override
    public Document get() {
        return documents.get(currentIndex);
    }
}
