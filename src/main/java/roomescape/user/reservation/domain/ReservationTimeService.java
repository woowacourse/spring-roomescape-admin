package roomescape.user.reservation.domain;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public Long saveReservationTime(final ReservationTime reservationTime) {
        return reservationTimeRepository.save(reservationTime);
    }

    public ReservationTime findReservationTime(final Long reservationTimeId) {
        return reservationTimeRepository.findById(reservationTimeId)
                .orElseThrow(() -> new IllegalStateException("Reservation time not found"));
    }

    public List<ReservationTime> findAllReservationTimes() {
        return reservationTimeRepository.findAll();
    }

    public void deleteReservationTime(final Long id) {
        reservationTimeRepository.deleteById(id);
    }
}
