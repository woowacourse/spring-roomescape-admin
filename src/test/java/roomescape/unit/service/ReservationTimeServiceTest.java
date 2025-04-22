package roomescape.unit.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import roomescape.dto.AddReservationTimeDto;
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
    void 과거_시간을_추가할_수_없다() {
        assertThat(reservationTimeService.allReservationTimes().size()).isEqualTo(0);
        reservationTimeService.addReservationTime(new AddReservationTimeDto(LocalTime.now().plusMinutes(30L)));
        assertThat(reservationTimeService.allReservationTimes().size()).isEqualTo(1);
    }

    @Test
    void 예약시간을_추가하고_조회할_수_있다() {
        assertThat(reservationTimeService.allReservationTimes().size()).isEqualTo(0);
        reservationTimeService.addReservationTime(new AddReservationTimeDto(LocalTime.now().plusMinutes(30L)));
        assertThat(reservationTimeService.allReservationTimes().size()).isEqualTo(1);
    }

    @Test
    void 예약시간을_삭제하고_조회할_수_있다() {
        long id = reservationTimeService.addReservationTime(
                new AddReservationTimeDto(LocalTime.now().plusMinutes(30L)));
        assertThat(reservationTimeService.allReservationTimes().size()).isEqualTo(1);
        reservationTimeService.deleteReservationTime(id);
        assertThat(reservationTimeService.allReservationTimes().size()).isEqualTo(0);
    }
}
