package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @Test
    void 이름이_4글자를_초과하여_예외가_발생한다() {
        //given
        final String name = "안녕하세요";

        //should
        assertThatIllegalArgumentException().isThrownBy(
                () -> new Reservation(name, LocalDate.now(), new ReservationTime(LocalTime.now())));
    }

    @Test
    void 이름이_빈_값이면_예외가_발생한다() {
        //given
        final String name = " ";

        //should
        assertThatIllegalArgumentException().isThrownBy(
                () -> new Reservation(name, LocalDate.now(), new ReservationTime(LocalTime.now())));
    }

    @Test
    void 날짜가_오늘보다_이전인_경우_예외가_발생한다() {
        // given
        LocalDate date = LocalDate.now().minusDays(1);

        // should
        assertThatIllegalArgumentException().isThrownBy(
                () -> new Reservation("히로", date, new ReservationTime(LocalTime.now())));
    }

    @Test
    void 시간이_지금보다_이전인_경우_예외가_발생한다() {
        // given
        final LocalDate now = LocalDate.now();
        ReservationTime reservationTime = new ReservationTime(LocalTime.now().minusHours(1));

        // should
        assertThatIllegalArgumentException().isThrownBy(() -> new Reservation("히로", now, reservationTime));
    }
}
