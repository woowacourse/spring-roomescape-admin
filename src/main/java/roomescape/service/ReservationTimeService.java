package roomescape.service;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@Service
@Validated
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTime> allReservationTimes() {
        return reservationTimeRepository.findAll();
    }

    public void deleteReservationTime(Long id) {
        reservationTimeRepository.deleteById(id);
    }

    public Long addReservationTime(@Valid ReservationTime reservationTime) {
        return reservationTimeRepository.add(reservationTime);
    }
}
