package roomescape.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationTest {

    @ValueSource(strings = {"", "   "})
    @ParameterizedTest
    void 예약자_이름은_공백일_수_없다(String name) {
        // Given
        // When
        // Then
        assertThatThrownBy(() -> new Reservation(name, LocalDate.now(), LocalTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약자 이름은 빈 칸일 수 없습니다.");
    }

    @Test
    void 예약자_이름과_예약_날짜_및_시간은_null일_수_없다() {
        // Given
        String name = "프리";
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        // When
        // Then
        assertThatThrownBy(() -> new Reservation(name, date, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약자 이름과 예약 날짜, 시간을 올바르게 입력해 주세요.");
        assertThatThrownBy(() -> new Reservation(name, null, time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약자 이름과 예약 날짜, 시간을 올바르게 입력해 주세요.");
        assertThatThrownBy(() -> new Reservation(null, date, time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약자 이름과 예약 날짜, 시간을 올바르게 입력해 주세요.");
    }

    @Test
    void 지정한_id를_가진_예약_객체를_생성한다() {
        // Given
        String name = "프리";
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        Reservation reservationExcludeIndex = new Reservation(name, date, time);

        // When
        Reservation reservationEntity = Reservation.toEntity(reservationExcludeIndex, 1L);

        // Then
        assertThat(reservationEntity.getId()).isEqualTo(1L);
        assertThat(reservationEntity.getName()).isEqualTo(name);
        assertThat(reservationEntity.getDate()).isEqualTo(date);
        assertThat(reservationEntity.getTime()).isEqualTo(time);
    }
}
