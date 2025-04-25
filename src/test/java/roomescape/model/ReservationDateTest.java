package roomescape.model;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationDateTest {
    @Test
    @DisplayName("날짜 형식 검증 - 통과")
    void test1() {
        assertThatCode(() -> new ReservationDate("2025-04-25")).doesNotThrowAnyException();
    }
    @Test
    @DisplayName("날짜 형식 검증 - 실패")
    void test2() {
        assertThatThrownBy(() -> new ReservationDate("2025_04_25")).hasMessage("Invalid date format");
        assertThatThrownBy(() -> new ReservationDate("이공이오-공사-이오")).hasMessage("Invalid date format");
        assertThatThrownBy(() -> new ReservationDate("이런건안되겠죠?")).hasMessage("Invalid date format");
    }
}
