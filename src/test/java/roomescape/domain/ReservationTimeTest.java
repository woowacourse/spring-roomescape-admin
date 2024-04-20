package roomescape.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.reservation.ReservationTime;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

class ReservationTimeTest {

    @DisplayName("실패 : 운영 시간 이외의 시간이 들어올 수 없다")
    @Test
    void should_ReturnIllegalArgumentException_When_GiveClosedTime() {
        LocalTime closedTime = ReservationTime.endTime().plusHours(1);

        assertThatThrownBy(() -> new ReservationTime(closedTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 가능 시간은 " + ReservationTime.startTime() + "부터 " + ReservationTime.endTime() + "까지입니다.");
    }

    @DisplayName("성공 : 운영 시간 사이의 시간이 들어올 수 있다")
    @Test
    void should_CreateReservationTime_When_GiveOpenTime() {
        LocalTime openTime = ReservationTime.endTime().minusHours(1);

        assertThatCode(() -> new ReservationTime(openTime)).doesNotThrowAnyException();
    }
}