package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(final ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTime> findAll() {
        return reservationTimeRepository.readAllReservationTimes();
    }

    public ReservationTime save(final ReservationTime reservationTime) {
        return reservationTimeRepository.createReservationTime(reservationTime);
    }

    public void removeById(final Long id) {
        reservationTimeRepository.deleteReservationTimeById(id);
    }
}
