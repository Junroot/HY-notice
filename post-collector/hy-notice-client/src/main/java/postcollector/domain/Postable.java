package postcollector.domain;

import core.domain.Board;
import core.domain.Post;

public interface Postable {

    Post convert(Board board);
}
