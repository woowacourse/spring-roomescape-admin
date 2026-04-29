package roomescape.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationTimeTest {

    @Test
    @DisplayName("ReservationTime의 id가 비어있는 경우 id를 할당한다.")
    public void bindId_success() throws Exception {
        // given
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(10, 0));

        long id = 1;
        // when
        reservationTime.bindId(id);

        // then
        assertThat(reservationTime.getId()).isEqualTo(id);
    }

    @Test
    @DisplayName("ReservationTime의 id가 할당되어 있는데 id를 할당하려는 경우 예외가 발생한다.")
    public void bindId_fail() throws Exception {
        // given
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.of(10, 0));

        long id = 2;

        // when then
        assertThatThrownBy(() -> reservationTime.bindId(id));
    }

}
