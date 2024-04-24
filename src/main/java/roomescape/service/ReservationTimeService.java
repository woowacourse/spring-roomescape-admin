package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.repository.ReservationTimeRepository;
import roomescape.model.ReservationTime;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTime> listReservationTimes() {
        return reservationTimeRepository.findAllReservationTimes();
    }

    public ReservationTime createReservationTime(final ReservationTime reservationTime) {
        return reservationTimeRepository.createReservationTime(reservationTime);
    }

    public void deleteReservationTime(final Long id) {
        reservationTimeRepository.deleteReservationTime(id);
    }
}
