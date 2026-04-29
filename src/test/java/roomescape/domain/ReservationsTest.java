package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReservationsTest {

    private final String customerName = "이프";
    private final LocalDateTime fixedTime = LocalDateTime.of(2026, 4, 29, 17, 0, 0, 0);

    @Test
    void 중복된_예약_일정이_있다면_참을_반환한다() {
        // given: 17:00 ~ 18:00 예약이 이미 있음
        ReservationTime alreadyTime = new ReservationTime(fixedTime, fixedTime.plusMinutes(60));
        Reservation alreadyReservation = new Reservation(customerName, alreadyTime);
        Reservations reservations = new Reservations(List.of(alreadyReservation));

        // when: 17:30 ~ 18:30 예약 시도
        ReservationTime newTime = new ReservationTime(fixedTime, fixedTime.plusMinutes(30));
        boolean result = reservations.hasOverlapTime(newTime);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 중복된_일정이_없다면_거짓을_반환한다() {
        // given: 17:00 ~ 18:00 예약이 이미 있음
        ReservationTime alreadyTime = new ReservationTime(fixedTime, fixedTime.plusMinutes(60));
        Reservation alreadyReservation = new Reservation(customerName, alreadyTime);
        Reservations reservations = new Reservations(new ArrayList<>(List.of(alreadyReservation)));

        // when: 18:00 ~ 19:00 예약 시도 (끝나는 시간과 시작 시간이 맞닿은 경우)
        ReservationTime newTime = new ReservationTime(fixedTime.plusMinutes(60), fixedTime.plusMinutes(120));
        boolean result = reservations.hasOverlapTime(newTime);

        // then
        assertThat(result).isFalse();
    }

    @Test
    void 일정이_비어있다면_거짓을_반환한다() {
        // given
        Reservations reservations = new Reservations(List.of());
        ReservationTime anyTime = new ReservationTime(fixedTime, fixedTime.plusMinutes(60));

        // when
        boolean result = reservations.hasOverlapTime(anyTime);

        // then
        assertThat(result).isFalse();
    }

    @Test
    void 기존_일정에_포함되는_시간대라면_참을_반환한다() {
        // given: 17:00 ~ 19:00
        ReservationTime alreadyTime = new ReservationTime(fixedTime, fixedTime.plusMinutes(60));
        Reservation alreadyReservation = new Reservation(customerName, alreadyTime);
        Reservations reservations = new Reservations(new ArrayList<>(List.of(alreadyReservation)));

        // when: 17:30 ~ 18:30
        ReservationTime newTime = new ReservationTime(fixedTime.plusMinutes(30), fixedTime.plusMinutes(150));
        boolean result = reservations.hasOverlapTime(newTime);

        // then
        assertThat(result).isTrue();
    }
}