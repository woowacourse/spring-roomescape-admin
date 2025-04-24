package roomescape.model;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReservationTimeTest {

    @Test
    void 시간은_null일_수_없다() {
        // Given
        // When
        // Then
        assertThatThrownBy(() -> new ReservationTime(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시간을 올바르게 입력해 주세요.");
    }

    @Test
    void 지정한_id를_가진_예약시간_엔티티를_생성한다() {
        // Given
        LocalTime time = LocalTime.now();
        ReservationTime reservationTime = new ReservationTime(time);

        // When
        ReservationTime reservationTimeEntity = reservationTime.toEntity(1L);

        // Then
        assertThat(reservationTimeEntity.getId()).isEqualTo(1L);
        assertThat(reservationTimeEntity.getStartAt()).isEqualTo(time);
    }

    @Test
    void 이미_엔티티화_되어있는_객체는_또다시_엔티티화_시킬_수_없다() {
        // Given
        LocalTime time = LocalTime.now();
        ReservationTime reservationTime = new ReservationTime(time);
        ReservationTime reservationTimeEntity = reservationTime.toEntity(1L);

        // When & Then
        assertThatThrownBy(() -> reservationTimeEntity.toEntity(2L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 Entity화 되어있는 객체입니다.");
    }
}
