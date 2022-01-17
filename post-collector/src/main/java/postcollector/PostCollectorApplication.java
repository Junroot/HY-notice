package postcollector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PostCollectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostCollectorApplication.class);
        //TODO MySQL로 제대로 DB에 저장되는지 확인하기
    }
}
