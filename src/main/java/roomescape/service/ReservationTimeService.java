package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTime> findAll() {
        return reservationTimeRepository.findAll();
    }

    public ReservationTime create(ReservationTime request) {
        return reservationTimeRepository.save(request);
    }

    public void delete(Long id) {
        reservationTimeRepository.deleteById(id);
    }
}
