package roomescape.dto.request;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ReservationRequestTest {

    @ParameterizedTest
    @ValueSource(strings = {"2000:11:02", "2000-11:02", "2000-11-2"})
    @DisplayName("Reservation date 검증 테스트")
    void validateDateTest(String date) {
        assertThatThrownBy(() -> ReservationRequest.of("코기", date, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("날짜는 0000-00-00 형식입니다");
    }
}