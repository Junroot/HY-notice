package core.domain;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class Posts {

    private final List<Post> elements;

    public Posts() {
        this.elements = new ArrayList<>();
    }

    public Posts(final List<Post> elements) {
        this.elements = new ArrayList<>(elements);
    }

    public Posts filterEqualOrAfter(final LocalDate localDate) {
        return elements.stream()
            .filter(post -> post.isEqualOrAfterDate(localDate))
            .collect(collectingAndThen(toList(), Posts::new));
    }

    public void addAll(final Posts posts) {
        elements.addAll(posts.getElements());
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }
}
