package hynotice.postcollector.domain;

import hynotice.core.domain.Board;
import hynotice.core.domain.Post;

public interface Postable {

    Post convert(Board board);
}
