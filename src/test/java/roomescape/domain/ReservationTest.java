package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.fixture.MemberFixture;
import roomescape.fixture.ReservationFixture;

class ReservationTest {

    @Test
    @DisplayName("관리자 또는 예약 주인은 예약을 취소할 수 있다.")
    void hasCancelPermission() {
        // given
        Member admin = MemberFixture.member1();
        Member owner = MemberFixture.member2();
        Member otherMember = MemberFixture.member3();
        Reservation reservation = ReservationFixture.reservation2();

        // when
        boolean result1 = reservation.hasCancelPermission(admin);
        boolean result2 = reservation.hasCancelPermission(owner);
        boolean result3 = reservation.hasCancelPermission(otherMember);

        // then
        assertThat(result1).isTrue();
        assertThat(result2).isTrue();
        assertThat(result3).isFalse();
    }
}
