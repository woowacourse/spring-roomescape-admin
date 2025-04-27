package roomescape.user.reservation.domain;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    @Transactional
    public Long saveReservationTime(final ReservationTime reservationTime) {
        return reservationTimeRepository.save(reservationTime);
    }

    @Transactional(readOnly = true)
    public ReservationTime findReservationTime(final Long reservationTimeId) {
        return reservationTimeRepository.findById(reservationTimeId)
                .orElseThrow(() -> new IllegalStateException("Reservation time not found"));
    }

    @Transactional(readOnly = true)
    public List<ReservationTime> findAllReservationTimes() {
        return reservationTimeRepository.findAll();
    }

    @Transactional
    public void deleteReservationTime(final Long id) {
        reservationTimeRepository.deleteById(id);
    }
}
