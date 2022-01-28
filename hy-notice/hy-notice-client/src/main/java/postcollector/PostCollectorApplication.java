package postcollector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaAuditing
@ComponentScan(basePackages = {"core"})
@SpringBootApplication
public class PostCollectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostCollectorApplication.class);
    }
}
