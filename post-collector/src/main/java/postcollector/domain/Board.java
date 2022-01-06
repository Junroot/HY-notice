package postcollector.domain;

public enum Board {
    CS_INFO("컴퓨터소프트웨어학부-학사일반"),
    CS_JOB("컴퓨터소프트웨어학부-취업정보"),
    CS_GRADUATE("컴퓨터소프트퉤어학부-졸업작품"),
    HY_GRADUATE("한양대학교-학사");

    private final String name;

    Board(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
