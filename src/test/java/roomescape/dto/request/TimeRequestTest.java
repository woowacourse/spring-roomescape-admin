package roomescape.dto.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    @DisplayName("잘못된 time 형식의 request가 들어오면 예외를 발생시킨다.")
    @Test
    void requestFail_when_invalidFormattedTime() {
        // given
        String startAt = "111:00";
        TimeRequest reservationRequest = new TimeRequest(startAt);

        // when & then
        assertThatThrownBy(reservationRequest::toDomain)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 startAt입니다: " + startAt);
    }

    @DisplayName("존재히지 않는 시간(25:00) request가 들어오면 예외를 발생시킨다.")
    @Test
    void requestFail_when_nonExistTime() {
        // given
        String startAt = "25:00";
        TimeRequest reservationRequest = new TimeRequest(startAt);

        // when & then
        assertThatThrownBy(reservationRequest::toDomain)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 startAt입니다: " + startAt);
    }
}
