package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationStatusTest {

    @Test
    @DisplayName("viewName으로 예약 상태를 찾는다.")
    void findByViewName() {
        // given
        String viewName = "예약";

        // when
        ReservationStatus result = ReservationStatus.findByViewName(viewName);

        // then
        assertThat(result).isEqualTo(ReservationStatus.RESERVED);
    }
}
