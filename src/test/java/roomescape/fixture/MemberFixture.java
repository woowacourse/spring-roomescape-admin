package roomescape.fixture;

import roomescape.domain.Member;
import roomescape.domain.Role;
import roomescape.service.dto.LoginRequest;

public abstract class MemberFixture {

    public static final int INITIAL_MEMBER_SIZE = 3;
    public static final long MEMBER_1_ID = 1;
    public static final long MEMBER_2_ID = 2;
    public static final long MEMBER_3_ID = 3;
    public static final String MEMBER_1_EMAIL = "kargo@google.com";
    public static final String MEMBER_2_EMAIL = "solar@google.com";
    public static final String MEMBER_3_EMAIL = "hotea@google.com";
    public static final String NOT_EXIST_EMAIL = "none@google.com";
    public static final String PASSWORD = "1234";
    public static final long NOT_EXIST_ID = 4;

    public static Member newMember(String name) {
        return new Member(name, NOT_EXIST_EMAIL, PASSWORD, Role.USER);
    }

    public static Member member1() {
        return new Member(MEMBER_1_ID, "kargo", MEMBER_1_EMAIL, PASSWORD, Role.ADMIN);
    }

    public static Member member2() {
        return new Member(MEMBER_2_ID, "solar", MEMBER_2_EMAIL, PASSWORD, Role.USER);
    }

    public static Member member3() {
        return new Member(MEMBER_3_ID, "hotea", MEMBER_3_EMAIL, PASSWORD, Role.USER);
    }

    public static Member newMemberWithoutId() {
        return new Member("newMemberName", NOT_EXIST_EMAIL, PASSWORD, Role.USER);
    }

    public static LoginRequest loginRequest1() {
        return new LoginRequest(MEMBER_1_EMAIL, PASSWORD);
    }
}
