package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeRepository;
import roomescape.entity.ReservationTime;
import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTime createReservationTime(ReservationTime reservationTime) {
        return reservationTimeRepository.createReservationTime(reservationTime);
    }

    public ReservationTime findReservationTime(Long id) {
        return reservationTimeRepository.findReservationTime(id);
    }

    public List<ReservationTime> findReservationTimes() {
        return reservationTimeRepository.findReservationTimes();
    }

    public void removeReservationTime(Long id) {
        reservationTimeRepository.removeReservationTime(id);
    }
}
