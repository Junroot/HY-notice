package hynotice.core.domain.repository;

import static hynotice.core.domain.QPost.post;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hynotice.core.domain.Keyword;
import hynotice.core.domain.Post;
import hynotice.core.domain.QPost;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class PostCustomRepositoryImpl implements PostCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public PostCustomRepositoryImpl(final JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Post> findAllByAnyIncludedAndExcludedKeywords(final List<Keyword> includedKeywords,
                                                              final List<Keyword> excludedKeywords,
                                                              int page, int pageSize) {
        return jpaQueryFactory.selectFrom(post)
            .where(
                containsAnyKeywords(includedKeywords)
                    .and(doesNotContainsAllKeywords(excludedKeywords))
            )
            .orderBy(post.createdAt.desc())
            .offset((long) (page - 1) * pageSize).limit(pageSize)
            .fetch();
    }

    private BooleanExpression containsAnyKeywords(final List<Keyword> keywords) {
        if (keywords.isEmpty()) {
            return Expressions.TRUE.isTrue();
        }

        BooleanExpression booleanExpression = Expressions.FALSE.isTrue();
        for (Keyword keyword : keywords) {
            booleanExpression = booleanExpression.or(post.title.contains(keyword.getValue()));
        }

        return booleanExpression;
    }

    private BooleanExpression doesNotContainsAllKeywords(final List<Keyword> keywords) {
        BooleanExpression booleanExpression = Expressions.TRUE.isTrue();

        for (Keyword keyword : keywords) {
            booleanExpression = booleanExpression.and(post.title.contains(keyword.getValue()).not());
        }

        return booleanExpression;
    }
}
