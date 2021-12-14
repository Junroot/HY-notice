package postcollector.presentation.controller;

import java.util.List;
import postcollector.domain.Post;

public class PostCollectorController {

    public static void run() {
        try {
            PostCollectorScanner postCollectorScanner = new PostCollectorScanner();
            List<Post> newPosts = postCollectorScanner.collectNewPosts();

            for (Post post : newPosts) {
                System.out.printf("%s\t%s\t%s%n", post.getTitle(), post.getUrl(), post.getWritingDate());
            }
        } catch (Exception e) {
            System.out.printf("[ERROR] %s%n", e.getMessage());
        }
    }
}
