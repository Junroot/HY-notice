package postcollector.domain;

public enum Board {
    CS_INFO("컴퓨터소프트웨어학부-학사일반"),
    CS_JOB("컴퓨터소프트웨어학부-취업정보"),
    CS_GRADUATE("컴퓨터소프트퉤어학부-졸업작품"),
    HY_GRADUATE("한양대학교-학사"),
    HY_ADMISSION("한양대학교-입학"),
    HY_RECRUITMENT("한양대학교-모집/채용"),
    HY_SERVICE("한양대학교-사회봉사"),
    HY_PUBLIC("한양대학교-일반"),
    HY_INDUSTRY("한양대학교-산학/연구"),
    HY_EVENT("한양대학교-행사"),
    HY_SCHOLARSHIP("한양대학교-장학"),
    HY_CONFERENCE("한양대학교-학회/세미나");

    private final String name;

    Board(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
