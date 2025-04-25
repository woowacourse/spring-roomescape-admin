package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @Test
    void 이름이_4글자를_초과하여_예외가_발생한다() {
        //given
        final String name = "안녕하세요";

        //should
        assertThatIllegalArgumentException().isThrownBy(
                () -> new Reservation(1L, name, LocalDate.now(), ReservationTime.of(LocalTime.MAX)));
    }

    @Test
    void 날짜가_오늘보다_이전인_경우_예외가_발생한다() {
        // given
        LocalDate date = LocalDate.now().minusDays(1);

        // should
        assertThatIllegalArgumentException().isThrownBy(
                () -> new Reservation(1L, "히로", date, ReservationTime.of(LocalTime.MAX)));
    }
}
