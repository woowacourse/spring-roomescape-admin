package roomescape.unit.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import roomescape.domain.ReservationTime;
import roomescape.service.ReservationTimeService;
import roomescape.unit.repository.FakeReservationTimeRepository;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReservationTimeServiceTest {

    static ReservationTimeService reservationTimeService;

    @BeforeEach
    void setup() {
        reservationTimeService = new ReservationTimeService(new FakeReservationTimeRepository());
    }

    @Test
    void 예약시간을_추가하고_조회할_수_있다() {
        assertThat(reservationTimeService.allReservationTimes().size()).isEqualTo(0);
        reservationTimeService.addReservationTime(new ReservationTime(1L, LocalTime.now()));
        assertThat(reservationTimeService.allReservationTimes().size()).isEqualTo(1);
    }

    @Test
    void 예약시간을_삭제하고_조회할_수_있다() {
        long id = reservationTimeService.addReservationTime(new ReservationTime(null, LocalTime.now().plusHours(1L)));
        assertThat(reservationTimeService.allReservationTimes().size()).isEqualTo(1);
        reservationTimeService.deleteReservationTime(id);
        assertThat(reservationTimeService.allReservationTimes().size()).isEqualTo(0);
    }
}
