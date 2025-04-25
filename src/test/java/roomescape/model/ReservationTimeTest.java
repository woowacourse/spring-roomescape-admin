package roomescape.model;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTimeTest {

    @Test
    @DisplayName("시간 형식 검증 - 통과")
    void test1() {
        assertThatCode(()->new ReservationTime(1L, "10:00")).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("시간 형식 검증 - 실패")
    void test2() {
        assertThatThrownBy(()->new ReservationTime(1L, "99:00")).hasMessage("Invalid time format");
        assertThatThrownBy(()->new ReservationTime(1L, "10-00")).hasMessage("Invalid time format");
        assertThatThrownBy(() -> new ReservationTime(1L, "이런건안되겠죠?")).hasMessage("Invalid time format");
    }

}
