package roomescape.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationTest {

    @DisplayName("인덱스를 입력하면 해당 아이디를 가진 Reservation 객체를 생성해서 반환한다.")
    @Test
    void initializeIndex() {
        // Given
        Reservation reservation = new Reservation
                (new ClientName("켈리"),
                        LocalDateTime.of(2023, 1, 12, 10, 12));
        Long initialIndex = 3L;

        // When
        Reservation initIndexReservation = reservation.initializeIndex(initialIndex);

        // Then
        assertThat(initIndexReservation.getId()).isEqualTo(initialIndex);
    }
}
