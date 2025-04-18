package roomescape.dto.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import roomescape.model.Reservation;

class ReservationRequestTest {

    @ParameterizedTest
    @ValueSource(strings = {"2000:11:02", "2000-11:02", "2000-11-2"})
    @DisplayName("Reservation date 검증 테스트")
    void validateDateTest(String date) {
        assertThatThrownBy(() -> new ReservationRequest(date, "11:30", "코기"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("날짜는 0000-00-00 형식입니다");
    }

    @ParameterizedTest
    @ValueSource(strings = {"24:30", "24:00", "11-30"})
    @DisplayName("Reservation time 검증 테스트")
    void validateTimeTest(String time) {
        assertThatThrownBy(() -> new ReservationRequest("2000-11-02", time, "코기"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시간은 00:00 형식입니다");
    }

    @Test
    @DisplayName("Reservation 변환 기능 테스트")
    void dtoToReservationWithoutIdTest() {
        // given
        ReservationRequest request = new ReservationRequest("2000-11-02", "11:30", "코기");
        // when
        Reservation reservation = request.dtoToReservationWithoutId();
        // then
        assertThat(reservation.getId()).isNull();
        assertThat(reservation.getReservationTime()).isEqualTo(LocalDateTime.of(2000, 11, 2, 11, 30));
        assertThat(reservation.getName()).isEqualTo("코기");
    }
}