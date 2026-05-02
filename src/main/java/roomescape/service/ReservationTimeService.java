package roomescape.service;

import java.time.LocalTime;
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

    public List<ReservationTime> allTimes() {
        return reservationTimeRepository.findAll();
    }

    public ReservationTime saveTime(LocalTime startAt) {
        long reservationTimeId = reservationTimeRepository.save(startAt);
        return reservationTimeRepository.findById(reservationTimeId);
    }

    public void removeTime(long timeId) {
        reservationTimeRepository.deleteById(timeId);
    }

    public ReservationTime findTime(long timeId) {
        return reservationTimeRepository.findById(timeId);
    }
}
