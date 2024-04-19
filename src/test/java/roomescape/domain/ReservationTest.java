package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @Test
    @DisplayName("문자열을 통해 예약을 생성한다.")
    void ReservationCreation() {
        // given
        Name expectedName = new Name("웨지");
        ReservationDate expectedDate = new ReservationDate("2024-04-18");
        ReservationTime expectedTime = new ReservationTime("15:30");
        // when
        Reservation actual = new Reservation("웨지", "2024-04-18", "15:30");
        // then
        assertAll(
                () -> assertThat(actual.getName()).isEqualTo(expectedName),
                () -> assertThat(actual.getReservationDate()).isEqualTo(expectedDate),
                () -> assertThat(actual.getReservationTime()).isEqualTo(expectedTime)
        );
    }
}
