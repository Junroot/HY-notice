package hynotice.core.domain;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum Board {
    CS_INFO(1L, "컴퓨터소프트웨어학부-학사일반"),
    CS_JOB(2L, "컴퓨터소프트웨어학부-취업정보"),
    CS_GRADUATE(3L, "컴퓨터소프트퉤어학부-졸업작품"),
    HY_GRADUATE(4L, "한양대학교-학사"),
    HY_ADMISSION(5L, "한양대학교-입학"),
    HY_RECRUITMENT(6L, "한양대학교-모집/채용"),
    HY_SERVICE(7L, "한양대학교-사회봉사"),
    HY_PUBLIC(8L, "한양대학교-일반"),
    HY_INDUSTRY(9L, "한양대학교-산학/연구"),
    HY_EVENT(10L, "한양대학교-행사"),
    HY_SCHOLARSHIP(11L, "한양대학교-장학"),
    HY_CONFERENCE(12L, "한양대학교-학회/세미나"),
    DORMITORY_DIRECT(13L, "학생생활관-대학직영기숙사"),
    DORMITORY_HAPPY(14L, "학생생활관-행복기숙사"),
    DORMITORY_RC(15L, "학생생활관-상담안내"),
    DORMITORY_STRUCTURE(16L, "학생생활관_자료안내"),
    DORMITORY_ENROLLED_STUDENT(17L, "학생생활관-학부 재학생/일반 대학원생"),
    DORMITORY_FRESHMAN(18L, "학생생활관-학부 신입생"),
    DORMITORY_ADDITION(19L, "학생생활관-추가모집");

    private static final Map<Long, Board> BOARDS = Arrays.stream(Board.values())
        .collect(Collectors.toMap(Board::getId, board -> board));

    private final Long id;
    private final String name;

    Board(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public static Board getById(final Long id) {
        return BOARDS.get(id);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
