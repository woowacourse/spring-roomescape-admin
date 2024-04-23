package roomescape.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationTest {

    @DisplayName("인덱스를 입력하면 해당 아이디를 가진 Reservation 객체를 생성해서 반환한다.")
    @Test
    void initializeIndex() {
        // Given
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.of(2, 22));
        Reservation reservation = new Reservation(
                new ClientName("켈리"),
                LocalDate.of(2023, 1, 12),
                reservationTime);

        Long initialIndex = 3L;

        // When
        Reservation initIndexReservation = reservation.initializeIndex(initialIndex);

        // Then
        assertThat(initIndexReservation.getId()).isEqualTo(initialIndex);
    }
}
