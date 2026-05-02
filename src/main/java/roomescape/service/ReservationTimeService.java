package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.controller.ReservationTimeRequest;
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

    public long saveTime(ReservationTimeRequest reservationTimeRequest) {
        return reservationTimeRepository.save(reservationTimeRequest);
    }

    public void removeTime(long timeId) {
        reservationTimeRepository.deleteById(timeId);
    }

    public ReservationTime findTime(long timeId) {
        return reservationTimeRepository.findById(timeId);
    }
}
