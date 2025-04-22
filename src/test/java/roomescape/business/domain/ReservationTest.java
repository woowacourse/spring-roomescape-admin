package roomescape.business.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

class ReservationTest {

    @Nested
    class 생성_테스트 {

        private final long id = 1L;
        private final Customer customer = new Customer("dompoo");
        private final LocalDate date = LocalDate.now().plusDays(20);
        private final ReservationTime time = new ReservationTime(1, LocalTime.of(10, 0));

        @Test
        void 아이디_예약자_날짜_예약시간으로_생성할_수_있다() {
            assertThatCode(() -> new Reservation(id, customer, date, time))
                    .doesNotThrowAnyException();
        }

        @Test
        void 예약자가_없으면_예외가_발생한다() {
            assertThatThrownBy(() -> new Reservation(id, null, date, time))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 날짜가_없으면_예외가_발생한다() {
            assertThatThrownBy(() -> new Reservation(id, customer, null, time))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 과거_날짜면_예외가_발생한다() {
            assertThatThrownBy(() -> new Reservation(id, customer, LocalDate.now().minusDays(1), time))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 시간이_없으면_예외가_발생한다() {
            assertThatThrownBy(() -> new Reservation(id, customer, date, null))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
