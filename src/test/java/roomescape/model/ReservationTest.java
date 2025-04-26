package roomescape.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationTest {

    @Test
    void 지정한_id를_가진_예약_엔티티를_생성한다() {
        // Given
        String name = "프리";
        LocalDate date = LocalDate.of(2025, 4, 24);
        LocalTime time = LocalTime.of(10, 0);
        ReservationTime reservationTime = new ReservationTime(new EntityId(1L), time);

        // When
        Reservation reservation = new Reservation(new EntityId(1L), name, date, reservationTime);

        // Then
        assertThat(reservation.getId()).isEqualTo(1L);
        assertThat(reservation.getName()).isEqualTo(name);
        assertThat(reservation.getDate()).isEqualTo(date);
        assertThat(reservation.getTime()).isEqualTo(reservationTime);
    }

    @ValueSource(strings = {"", "   "})
    @ParameterizedTest
    void 예약자_이름은_공백일_수_없다(String name) {
        // Given
        LocalTime time = LocalTime.of(10, 0);
        ReservationTime reservationTime = new ReservationTime(EntityId.generateUnassigned(), time);

        // When
        // Then
        assertThatThrownBy(() -> new Reservation(EntityId.generateUnassigned(), name, LocalDate.of(2025, 4, 24), reservationTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약자 이름은 빈 칸일 수 없습니다.");
    }

    @Test
    void id와_예약자_이름과_예약_날짜_및_시간은_null일_수_없다() {
        // Given
        String name = "프리";
        LocalDate date = LocalDate.of(2025, 4, 24);
        LocalTime time = LocalTime.of(10, 0);
        ReservationTime reservationTime = new ReservationTime(EntityId.generateUnassigned(), time);
        // When
        // Then
        assertThatThrownBy(() -> new Reservation(null, name, date, reservationTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("id와 예약자 이름, 예약 날짜, 시간을 올바르게 입력해 주세요.");
        assertThatThrownBy(() -> new Reservation(EntityId.generateUnassigned(), name, date, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("id와 예약자 이름, 예약 날짜, 시간을 올바르게 입력해 주세요.");
        assertThatThrownBy(() -> new Reservation(EntityId.generateUnassigned(), name, null, reservationTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("id와 예약자 이름, 예약 날짜, 시간을 올바르게 입력해 주세요.");
        assertThatThrownBy(() -> new Reservation(EntityId.generateUnassigned(), null, date, reservationTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("id와 예약자 이름, 예약 날짜, 시간을 올바르게 입력해 주세요.");
    }
}
