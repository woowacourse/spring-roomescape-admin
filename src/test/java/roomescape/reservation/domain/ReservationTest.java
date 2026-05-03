package roomescape.reservation.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.reservationtime.domain.ReservationTime;

class ReservationTest {

    private final ReservationTime reservationTime = ReservationTime.builder()
            .id(1L)
            .startAt(LocalTime.of(15, 0))
            .build();

    @Test
    @DisplayName("이름이 두 글자 미만(1자)이면 에러가 발생한다")
    void 이름_길이_한_글자_에러_발생() {
        assertThatThrownBy(() -> Reservation.builder()
                .name("산")
                .date(LocalDate.now())
                .time(reservationTime)
                .build())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이름이 다섯 글자를 초과(6자)하면 에러가 발생한다")
    void 이름_길이_여섯_글자_에러_발생() {
        assertThatThrownBy(() -> Reservation.builder()
                .name("LEESAN")
                .date(LocalDate.now())
                .time(reservationTime)
                .build())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이름이 두 글자이면 정상 생성된다")
    void 이름_길이_경계값_두_글자_정상_생성() {
        Reservation reservation = Reservation.builder()
                .id(1L)
                .name("이산")
                .date(LocalDate.now())
                .time(reservationTime)
                .build();

        assertThat(reservation.getName()).isEqualTo("이산");
    }

    @Test
    @DisplayName("이름이 다섯 글자이면 정상 생성된다")
    void 이름_길이_경계값_다섯_글자_정상_생성() {
        Reservation reservation = Reservation.builder()
                .id(1L)
                .name("레오나르도")
                .date(LocalDate.now())
                .time(reservationTime)
                .build();

        assertThat(reservation.getName()).isEqualTo("레오나르도");
    }

    @Test
    @DisplayName("예약 날짜가 과거의 날짜이면 에러가 발생한다")
    void 과거_날짜_에러_발생() {
        assertThatThrownBy(() -> Reservation.builder()
                .name("이산")
                .date(LocalDate.now().minusDays(1))
                .time(reservationTime)
                .build())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예약 날짜가 오늘 이후의 날짜이면 정상 생성된다")
    void 예약_날짜_오늘_이후_정상_생성() {
        LocalDate today = LocalDate.now();
        Reservation reservation = Reservation.builder()
                .name("쿠리만쥬")
                .date(today)
                .time(reservationTime)
                .build();
        assertThat(reservation.getDate()).isEqualTo(today);
    }
}
