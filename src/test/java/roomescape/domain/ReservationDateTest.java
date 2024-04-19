package roomescape.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

class ReservationDateTest {

    @DisplayName("실패 : 현재 날짜보다 이전의 날짜가 들어올 수 없다")
    @Test
    void should_ReturnIllegalArgumentException_When_GiveEarlierDateThanNow() {
        LocalDate earlierDate = LocalDate.now().plusDays(-1);
        assertThatThrownBy(() -> new ReservationDate(earlierDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약은 현재 이후 날짜만 가능합니다.");
    }

    @DisplayName("성공 : 현재 날짜보다 이후 날짜는 예약할 수 있다")
    @Test
    void should_CreateReservationDate_When_GiveEarlierDateThanNow() {
        LocalDate laterDate = LocalDate.now().plusDays(1);
        assertThatCode(() -> new ReservationDate(laterDate)).doesNotThrowAnyException();

    }
}