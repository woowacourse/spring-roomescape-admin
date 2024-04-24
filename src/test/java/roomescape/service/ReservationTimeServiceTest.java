package roomescape.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimeRepository;
import roomescape.support.IntegrationTestSupport;

class ReservationTimeServiceTest extends IntegrationTestSupport {

    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationTimeService target;

    @DisplayName("예약 시간을 사용하고 있는 예약이 존재한다면, 예약을 삭제할 수 없다.")
    @Test
    void deleteUsedTime() {
        ReservationTime savedTime = reservationTimeRepository.save(createReservationTime());
        reservationRepository.save(createReservation(savedTime));

        assertThatThrownBy(() -> target.deleteBy(savedTime.getId()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("다른 예약에서 해당 예약 시간을 사용하고 있습니다.");
    }

    private ReservationTime createReservationTime() {
        return new ReservationTime(LocalTime.now());
    }

    private Reservation createReservation(ReservationTime reservationTime) {
        return new Reservation(new Name("아톰"), LocalDate.now(), reservationTime);
    }
}
