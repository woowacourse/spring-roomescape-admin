package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationRepository;
import roomescape.entity.Reservation;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.createReservation(reservation);
    }

    public List<Reservation> findReservations() {
        return reservationRepository.findReservations();
    }

    public void removeReservation(Long id) {
        reservationRepository.removeReservation(id);
    }
}
