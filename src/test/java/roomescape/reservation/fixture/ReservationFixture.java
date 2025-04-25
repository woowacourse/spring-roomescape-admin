package roomescape.reservation.fixture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.dto.ReservationReqDto;
import roomescape.reservationTime.domain.ReservationTime;
import roomescape.reservationTime.repository.ReservationTimeRepository;

import java.time.LocalDate;

@Component
@Transactional
public class ReservationFixture {

    @Autowired
    private static ReservationTimeRepository reservationTimeRepository;

    public static Reservation createReservation(String name, LocalDate date, ReservationTime reservationTime) {
        return Reservation.of(name, date, reservationTime);
    }

    public static ReservationReqDto createReqDto(String name, LocalDate date, Long timeId) {
        return new ReservationReqDto(name, date, timeId);
    }
}
