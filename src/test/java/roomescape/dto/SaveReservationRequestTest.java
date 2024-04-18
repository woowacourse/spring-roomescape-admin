package roomescape.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class SaveReservationRequestTest {

    @DisplayName("SaveReservationRequest를 Reservation으로 변환한다.")
    @Test
    void toReservationTest() {
        // Given
        SaveReservationRequest request = new SaveReservationRequest(
                LocalDate.of(2023, 12, 1),
                "켈리",
                LocalTime.of(1, 12)
        );

        // When
        Reservation reservation = request.toReservation();

        // Then
        assertThat(reservation).isNotNull();
    }
}
