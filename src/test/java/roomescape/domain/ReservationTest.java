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
        assertThatIllegalArgumentException().isThrownBy(() -> new Reservation(name, LocalDate.now(), LocalTime.now()));
    }

}
