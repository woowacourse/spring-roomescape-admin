package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RoomescapeTest {

    @Test
    void 일정이_빈_방탈출에서_예약시간과_예약자가_주어지면_방탈출_예약을_할_수_있다() {
        // given
        ReservationSchedule emptySchedule = new ReservationSchedule(new ArrayList<>());
        Roomescape roomescape = new Roomescape(emptySchedule);

        ReservationTime reservationTime = new ReservationTime(LocalDateTime.of(2026, 4, 29, 17, 0));
        String customerName = "이프";

        // when
        Reservation reservation = roomescape.reserve(customerName, reservationTime);

        // then
        assertThat(reservation)
                .extracting(Reservation::getCustomerName, Reservation::getReservationTime)
                .containsExactly(customerName, reservationTime);

    }

    @ParameterizedTest
    @ValueSource(longs = {0, 59})
    void 한시간짜리_방탈출_예약시_예약시간과_겹치는_일정이_있다면_예외가_발생한다(long minuteDuration) {
        // given: 0분 ~ 59분 차이의 예약 시간 주어짐.
        LocalDateTime fixedTime = LocalDateTime.of(2026, 4, 29, 17, 0);
        ReservationTime reservedTime = new ReservationTime(fixedTime);
        String customerName = "이프";

        Reservation alreadyReservation = new Reservation(customerName, reservedTime);
        ReservationSchedule conflictSchedule = new ReservationSchedule(List.of(alreadyReservation));
        Roomescape roomescape = new Roomescape(conflictSchedule);

        ReservationTime reservationTime = new ReservationTime(fixedTime.plusMinutes(minuteDuration));

        // when & then
        assertThatThrownBy(() -> roomescape.reserve(customerName, reservationTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 시간에 이미 예약된 정보가 있습니다.");
    }

    @Test
    void 한시간짜리_방탈출_예약시_기존_예약_일정에서_한시간이_지났다면_예약할_수_있다() {
        // given: 0분 ~ 59분 차이의 예약 시간 주어짐.
        LocalDateTime fixedTime = LocalDateTime.of(2026, 4, 29, 17, 0);
        ReservationTime reservedTime = new ReservationTime(fixedTime);
        String customerName = "이프";

        Reservation alreadyReservation = new Reservation(customerName, reservedTime);
        ReservationSchedule conflictSchedule = new ReservationSchedule(List.of(alreadyReservation));
        Roomescape roomescape = new Roomescape(conflictSchedule);

        ReservationTime reservationTime = new ReservationTime(fixedTime.plusMinutes(60));

        // when
        Reservation reservation = roomescape.reserve(customerName, reservationTime);

        // then
        assertThat(reservation)
                .extracting(Reservation::getCustomerName, Reservation::getReservationTime)
                .containsExactly(customerName, reservationTime);
    }
}