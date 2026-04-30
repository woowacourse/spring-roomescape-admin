package roomescape.reservationtime;

import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTime> findAll() {
        return reservationTimeRepository.findAll();
    }

    public ReservationTime findById(Long id) {
        return reservationTimeRepository.findById(id);
    }

    @Transactional
    public ReservationTime save(LocalTime startAt) {
        return reservationTimeRepository.save(startAt);
    }

    @Transactional
    public void delete(Long id) {
        reservationTimeRepository.delete(id);
    }
}
