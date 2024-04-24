package roomescape.admin.reservation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.admin.reservation.entity.ReservationTime;
import roomescape.admin.reservation.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTime> findAll() {
        return reservationTimeRepository.findAll();
    }

    public ReservationTime create(ReservationTime reservationTime) {
        return reservationTimeRepository.save(new ReservationTime(reservationTime.getStartAt()));
    }

    public int delete(Long id) {
        return reservationTimeRepository.delete(id);
    }
}
