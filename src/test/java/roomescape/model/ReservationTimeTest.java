package roomescape.model;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReservationTimeTest {

    @Test
    void 지정한_id를_가진_예약시간_엔티티를_생성한다() {
        // Given
        LocalTime time = LocalTime.of(10, 0);

        // When
        ReservationTime reservationTime = new ReservationTime(EntityId.generate(1L), time);

        // Then
        assertThat(reservationTime.getId()).isEqualTo(1L);
        assertThat(reservationTime.getStartAt()).isEqualTo(time);
    }

    @Test
    void id와_시간은_null일_수_없다() {
        // Given
        // When
        // Then
        assertThatThrownBy(() -> new ReservationTime(null, LocalTime.of(10, 0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("id를 올바르게 입력해 주세요.");
        assertThatThrownBy(() -> new ReservationTime(EntityId.generateUnassigned(), null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시간을 올바르게 입력해 주세요.");
    }
}
