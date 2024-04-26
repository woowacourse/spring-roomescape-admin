package roomescape.entity;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.entity.exception.ReservationDatePassedException;

class ReservationDateTest {
    @DisplayName("날짜가 null인 경우 ReservationDate 생성에 실패한다")
    @Test
    void createReservationDateWithNullTest() {
        assertThatThrownBy(() -> new ReservationDate(null))
                .isInstanceOf(NullPointerException.class);
    }

    @DisplayName("날짜가 현재 날짜보다 이전 날짜인 경우 생성 시 예외가 발생한다")
    @Test
    void createReservationDateWithPassedDateTest() {
        LocalDate now = LocalDate.now();
        LocalDate passedDate = now.minusDays(1);
        assertThatThrownBy(() -> new ReservationDate(passedDate))
                .isInstanceOf(ReservationDatePassedException.class);
    }
}
