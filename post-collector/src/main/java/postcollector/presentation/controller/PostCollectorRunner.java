package postcollector.presentation.controller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PostCollectorRunner implements CommandLineRunner {

    private final PostCollectorController postCollectorController;

    public PostCollectorRunner(final PostCollectorController postCollectorController) {
        this.postCollectorController = postCollectorController;
    }

    @Override
    public void run(final String... args) {
        System.out.println("[커맨드라인러너 시작]");
        postCollectorController.collectAndSavePosts();
        System.out.println("[커맨드라인러너 끝]");
    }
}
