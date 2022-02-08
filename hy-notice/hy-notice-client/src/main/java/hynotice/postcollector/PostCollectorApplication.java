package hynotice.postcollector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaAuditing
@EntityScan("hynotice")
@EnableJpaRepositories(basePackages = "hynotice")
@SpringBootApplication(scanBasePackages = "hynotice")
public class PostCollectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostCollectorApplication.class);
    }
}
