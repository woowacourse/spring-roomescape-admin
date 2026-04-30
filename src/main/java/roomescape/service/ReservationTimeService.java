package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTime create(ReservationTime reservationTime) {
        return reservationTimeRepository.save(reservationTime);
    }

    public List<ReservationTime> readAll() {
        return reservationTimeRepository.findAll();
    }

    public void delete(Long id) {
        reservationTimeRepository.deleteById(id);
    }
}
