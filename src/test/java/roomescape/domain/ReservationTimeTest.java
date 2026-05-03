package roomescape.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReservationTimeTest {

    @Test
    @DisplayName("시작 시간이 null이면 예외가 발생한다")
    void startAtNull() {
        assertThatThrownBy(() -> new ReservationTime(null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 시간은 필수입니다.");
    }

    @Test
    @DisplayName("유효한 값으로 예약 시간을 생성할 수 있다")
    void createSuccess() {
        LocalTime time = LocalTime.of(10, 15);

        ReservationTime reservationTime = new ReservationTime(1L, time);

        assertThat(reservationTime.getStartAt()).isEqualTo(time);
    }

    @Test
    @DisplayName("toEntity로 id가 부여된 예약 시간을 생성할 수 있다")
    void toEntity() {
        ReservationTime original = new ReservationTime(null, LocalTime.of(14, 30));

        ReservationTime entity = ReservationTime.toEntity(original, 5L);

        assertThat(entity.getId()).isEqualTo(5L);
        assertThat(entity.getStartAt()).isEqualTo(original.getStartAt());
    }
}
