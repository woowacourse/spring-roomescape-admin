package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.reservation.ReservationTime;
import roomescape.repository.ReservationTimeRepository;
import roomescape.repository.web.dao.ReservationTimeDao;

import java.util.List;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeDao reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTime addTime(ReservationTime reservationTime) {
        return reservationTimeRepository.add(reservationTime);
    }

    public List<ReservationTime> allReservationTimes() {
        return reservationTimeRepository.findAll();
    }

    public void delete(Long id) {
        reservationTimeRepository.delete(id);
    }
}
