package roomescape.dto.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.model.ReservationTime;

class TimeRequestTest {

    @DisplayName("request를 ReservationTime으로 변경한다.")
    @Test
    void request_toReservation() {
        // given
        TimeRequest reservationRequest = new TimeRequest(
                "10:00"
        );

        // when
        ReservationTime reservationTime = reservationRequest.toDomain();

        // then
        assertAll(
                () -> assertThat(reservationTime.getId()).isNull(),
                () -> assertThat(reservationTime.getStartAt()).isEqualTo(LocalTime.of(10,0))
        );
    }
}
