package roomescape.dto;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReservationRequestTest {

    @Test
    void 예약자_이름이_null이면_예외가_발생한다() {
        String name = null;
        LocalDate date = LocalDate.now();
        Long timeId = 1L;

        assertThatThrownBy(() -> new ReservationRequest(name, date, timeId))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 예약자_이름이_빈칸이면_예외가_발생한다() {
        String name = "";
        LocalDate date = LocalDate.now();
        Long timeId = 1L;

        assertThatThrownBy(() -> new ReservationRequest(name, date, timeId))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 예약일자가_null이면_예외가_발생한다() {
        String name = "듀이";
        LocalDate date = null;
        Long timeId = 1L;

        assertThatThrownBy(() -> new ReservationRequest(name, date, timeId))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
