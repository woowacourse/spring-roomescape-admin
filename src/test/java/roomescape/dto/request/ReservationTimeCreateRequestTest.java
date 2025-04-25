package roomescape.dto.request;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTimeCreateRequestTest {

    @DisplayName("시간이 비어있으면 예외가 발생한다.")
    @Test
    void validateTimeNullThrowExceptionTest() {

        // given & when & then
        assertThatThrownBy(() -> new ReservationTimeCreateRequest(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("빈 값으로 예약할 수 없습니다.");
    }

}
