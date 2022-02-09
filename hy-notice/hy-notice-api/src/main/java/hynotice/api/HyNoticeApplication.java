package hynotice.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("hynotice")
@EnableJpaRepositories(basePackages = "hynotice")
@SpringBootApplication(scanBasePackages = "hynotice")
public class HyNoticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(HyNoticeApplication.class);
    }
}
