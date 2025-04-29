package roomescape.user.reservation.domain;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Transactional
    public Long saveReservation(final Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Transactional(readOnly = true)
    public Reservation findReservation(final Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalStateException("Reservation not found"));
    }

    @Transactional(readOnly = true)
    public List<Reservation> findReservations() {
        return reservationRepository.findAll();
    }

    @Transactional
    public void deleteReservation(final Long id) {
        reservationRepository.deleteById(id);
    }
}
