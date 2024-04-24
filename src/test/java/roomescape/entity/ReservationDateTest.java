package roomescape.entity;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationDateTest {
    @DisplayName("날짜가 null인 경우 ReservationDate 생성에 실패한다")
    @Test
    void createReservationDateWithNull() {
        assertThatThrownBy(() -> new ReservationDate(null))
                .isInstanceOf(NullPointerException.class);
    }
}
