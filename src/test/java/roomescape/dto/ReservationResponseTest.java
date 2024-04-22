package roomescape.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.ClientName;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationResponseTest {

    @DisplayName("Reservation을 입력받으면 ReservationResponse로 변환한다.")
    @Test
    void convertDtoTest() {
        // Given
        Reservation reservation = new Reservation(
                new ClientName("켈리"),
                LocalDate.of(2023, 1, 12),
                LocalTime.of(1, 12)
        );

        // When
        ReservationResponse reservationResponse = ReservationResponse.from(reservation);

        // Then
        assertThat(reservationResponse).isNotNull();
    }
}
