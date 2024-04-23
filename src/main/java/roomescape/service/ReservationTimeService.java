package roomescape.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationTimeCreateRequest;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimes;

@Service
public class ReservationTimeService {

    private final ReservationTimes reservationTimes;

    public ReservationTimeService(ReservationTimes reservationTimes) {
        this.reservationTimes = reservationTimes;
    }

    public List<ReservationTime> findReservationTimes() {
        return reservationTimes.findReservationTimes();
    }

    public Optional<ReservationTime> findReservationTimeById(Long createdReservationTimeId) {
        return reservationTimes.findReservationTimeById(createdReservationTimeId);
    }

    public ReservationTime createReservationTime(ReservationTimeCreateRequest reservationTimeCreateRequest) {
        return reservationTimes.createReservationTime(reservationTimeCreateRequest);
    }

    public void deleteReservationTimeById(Long id) {
        reservationTimes.deleteReservationTimeById(id);
    }
}
