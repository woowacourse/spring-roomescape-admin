package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.entity.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTime> findAll() {
        return reservationTimeRepository.findAll();
    }

    public ReservationTime save(ReservationTime reservationTime) {
        return reservationTimeRepository.save(reservationTime);
    }

    public void delete(Long id) {
        reservationTimeRepository.delete(id);
    }
}
