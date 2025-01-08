package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.fixture.MemberFixture;
import roomescape.fixture.ReservationFixture;
import roomescape.fixture.ThemeFixture;

class ReservationTest {

    @Test
    @DisplayName("예약 시간이 특정 시간보다 이전이면 true를 반환한다.")
    void isBeforeTrue() {
        // given
        LocalDate date = LocalDate.now();
        LocalTime early = LocalTime.of(10, 0);
        LocalTime late = LocalTime.of(10, 1);

        ReservationTime time = new ReservationTime(early);
        Reservation reservation = new Reservation(date, MemberFixture.member1(), time, ThemeFixture.theme1());

        // when 
        boolean result = reservation.isBefore(LocalDateTime.of(date, late));

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("예약 시간이 특정 시간보다 이후이면 false를 반환한다.")
    void isBeforeFalse() {
        // given
        LocalDate date = LocalDate.now();
        LocalTime early = LocalTime.of(10, 0);
        LocalTime late = LocalTime.of(10, 1);

        ReservationTime time = new ReservationTime(late);
        Reservation reservation = new Reservation(date, MemberFixture.member1(), time, ThemeFixture.theme1());

        // when
        boolean result = reservation.isBefore(LocalDateTime.of(date, early));

        // then
        assertThat(result).isFalse();
    }

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
