package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

    private static final ReservationTime VALID_TIME = new ReservationTime(1L, LocalTime.of(10, 0));
    private static final LocalDate VALID_DATE = LocalDate.of(2024, 1, 1);

    @Test
    @DisplayName("이름이 null 이면 예외가 발생한다")
    void create_throwsWhenNameIsNull() {
        assertThatThrownBy(() -> new Reservation(null, VALID_DATE, VALID_TIME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약자 이름은 필수입니다.");
    }

    @Test
    @DisplayName("이름이 공백이면 예외가 발생한다")
    void create_throwsWhenNameIsBlank() {
        assertThatThrownBy(() -> new Reservation("   ", VALID_DATE, VALID_TIME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약자 이름은 필수입니다.");
    }

    @Test
    @DisplayName("날짜가 null 이면 예외가 발생한다")
    void create_throwsWhenDateIsNull() {
        assertThatThrownBy(() -> new Reservation("브라운", null, VALID_TIME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 날짜는 필수입니다.");
    }

    @Test
    @DisplayName("예약 시간이 null 이면 예외가 발생한다")
    void create_throwsWhenTimeIsNull() {
        assertThatThrownBy(() -> new Reservation("브라운", VALID_DATE, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 시간은 필수입니다.");
    }
}
