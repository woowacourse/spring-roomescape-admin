package roomescape.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalTime;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import roomescape.fixture.ReservationDateFixture;
import roomescape.fixture.ReservationNameFixture;

class ReservationTest {

    private final Long id = 1L;
    private final ReservationName name = ReservationNameFixture.한스;
    private final ReservationDate date = ReservationDateFixture.예약일_25_4_22;
    private final ReservationTime time = new ReservationTime(1L, LocalTime.of(14, 30));

    @Test
    void 예약을_올바르게_생성한다() {
        // when
        Reservation reservation = new Reservation(id, name, date, time);

        // then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(reservation.id()).isEqualTo(id);
        softly.assertThat(reservation.name()).isEqualTo(name.getValue());
        softly.assertThat(reservation.date()).isEqualTo(date.date());
        softly.assertThat(reservation.time()).isEqualTo(time.time());
        softly.assertThat(reservation.reservationTime()).isEqualTo(time);
        softly.assertAll();
    }

    @Test
    void ID가_null이면_예외가_발생한다() {
        assertThatThrownBy(() -> new Reservation(null, name, date, time))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("예약의 id는 null일 수 없습니다.");
    }

    @Test
    void 이름이_null이면_예외가_발생한다() {
        assertThatThrownBy(() -> new Reservation(id, null, date, time))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("예약의 이름는 null일 수 없습니다.");
    }

    @Test
    void 날짜가_null이면_예외가_발생한다() {
        assertThatThrownBy(() -> new Reservation(id, name, null, time))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("예약의 날짜는 null일 수 없습니다.");
    }

    @Test
    void 시간이_null이면_예외가_발생한다() {
        assertThatThrownBy(() -> new Reservation(id, name, date, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("예약의 시간는 null일 수 없습니다.");
    }
}
