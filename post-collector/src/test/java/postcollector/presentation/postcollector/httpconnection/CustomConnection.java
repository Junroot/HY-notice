package postcollector.presentation.postcollector.httpconnection;

import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;

public class CustomConnection extends HttpConnection {

    private final Document document;

    public CustomConnection(final Document document) {
        this.document = document;
    }

    @Override
    public Document get() {
        return document;
    }
}
