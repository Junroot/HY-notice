package hynotice.api.presentation.dto;

import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Item;
import hynotice.core.domain.Post;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

public class PostRssView extends AbstractRssFeedView {

    private static final String AUTHOR = "junroot0909@gmail.com";
    private static final String FEED_TITLE = "Hanyang University Notice RSS Feed";
    private static final String FEED_LINK = "https://www.hanyang.ac.kr";
    private final List<Item> items;

    public PostRssView(final List<Post> posts) {
        items = posts.stream()
            .map(this::convertPostToItem)
            .collect(Collectors.toList());
    }

    private Item convertPostToItem(final Post post) {
        Item entryOne = new Item();
        entryOne.setTitle(post.getTitle());
        entryOne.setAuthor(AUTHOR);
        entryOne.setLink(post.getUrl());
        entryOne.setPubDate(Date.valueOf(post.getWritingDate()));
        return entryOne;
    }

    @Override
    protected void buildFeedMetadata(final Map<String, Object> model,
                                     final Channel feed, HttpServletRequest request) {
        feed.setFeedType("rss_2.0");
        feed.setTitle(FEED_TITLE);
        feed.setDescription(FEED_TITLE);
        feed.setLink(FEED_LINK);
        setContentType(MediaType.TEXT_XML_VALUE);
    }

    @Override
    protected List<Item> buildFeedItems(final Map<String, Object> model,
                                        final HttpServletRequest request,
                                        final HttpServletResponse response) {
        return items;
    }
}
