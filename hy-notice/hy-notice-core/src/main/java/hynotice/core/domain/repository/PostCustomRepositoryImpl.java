package hynotice.core.domain.repository;

import static hynotice.core.domain.QPost.post;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hynotice.core.domain.Post;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class PostCustomRepositoryImpl implements PostCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public PostCustomRepositoryImpl(final JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Post> findAllByAnyKeyword(final List<String> keywords) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        for (String keyword : keywords) {
            booleanBuilder.or(post.title.contains(keyword));
        }

        return jpaQueryFactory.selectFrom(post)
            .where(booleanBuilder)
            .orderBy(post.writingDate.desc())
            .fetch();
    }
}
