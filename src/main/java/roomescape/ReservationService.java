package roomescape;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public Reservation findReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Long createReservation(String reservationName, LocalDateTime reservationDateTime) {
        final Reservation reservation = new Reservation(reservationName, reservationDateTime);
        return reservationRepository.save(reservation);
    }

    public void deleteReservation(long id) {
        reservationRepository.deleteById(id);
    }
}
