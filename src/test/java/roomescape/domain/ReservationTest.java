package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @DisplayName("두 예약이 서로 중복된 예약인지 판단한다.")
    @Test
    void isDuplicateReservation() {
        // given
        LocalDate date = LocalDate.of(2025, 4, 18);
        LocalTime time = LocalTime.of(8, 30);
        Reservation reservation = new Reservation("제프리", date, time);
        Reservation duplicated = new Reservation("플린트", date, time);

        // when
        boolean isDuplicated = reservation.isDuplicateReservation(duplicated);

        // then
        assertThat(isDuplicated).isTrue();
    }

    @DisplayName("날짜가 다르면 중복이 아니다.")
    @Test
    void isNotDuplicate_whenDateIsDifferent() {
        //given
        LocalDate date1 = LocalDate.of(2025, 4, 18);
        LocalDate date2 = LocalDate.of(2025, 4, 19);
        LocalTime time = LocalTime.of(8, 30);
        Reservation reservation1 = new Reservation("제프리", date1, time);
        Reservation reservation2 = new Reservation("플린트", date2, time);

        //when
        boolean isDuplicated = reservation1.isDuplicateReservation(reservation2);

        //then
        assertThat(isDuplicated).isFalse();
    }

    @DisplayName("시간이 다르면 중복이 아니다.")
    @Test
    void isNotDuplicate_whenTimeIsDifferent() {
        //given
        LocalDate date = LocalDate.of(2025, 4, 18);
        LocalTime time1 = LocalTime.of(8, 30);
        LocalTime time2 = LocalTime.of(9, 0);
        Reservation reservation1 = new Reservation("제프리", date, time1);
        Reservation reservation2 = new Reservation("플린트", date, time2);

        //when
        boolean isDuplicated = reservation1.isDuplicateReservation(reservation2);

        //then
        assertThat(isDuplicated).isFalse();
    }

}
