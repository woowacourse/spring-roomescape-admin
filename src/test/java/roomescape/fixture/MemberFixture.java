package roomescape.fixture;

import roomescape.domain.Member;
import roomescape.service.dto.LoginRequest;

public abstract class MemberFixture {

    public static final int INITIAL_RESERVATION_SIZE = 1;
    public static final long MEMBER_1_ID = 1;
    public static final String MEMBER_1_EMAIL = "kargo@google.com";
    public static final String NOT_EXIST_EMAIL = "none@google.com";
    public static final String PASSWORD = "1234";
    public static final long NOT_EXIST_ID = 2;

    public static Member member1() {
        return new Member(MEMBER_1_ID, "kargo", MEMBER_1_EMAIL, PASSWORD);
    }

    public static Member newMemberWithoutId() {
        return new Member("solar", "solar@google.com", "1234");
    }

    public static LoginRequest loginRequest1() {
        return new LoginRequest(MEMBER_1_EMAIL, PASSWORD);
    }
}
