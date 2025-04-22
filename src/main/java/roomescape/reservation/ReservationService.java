package roomescape.reservation;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.model.Reservation;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    public Reservation addReservation(ReservationRequest reservationRequest) {
        return reservationRepository.insertReservation(reservationRequest);
    }

    public boolean deleteReservationById(long id) {
        return reservationRepository.deleteReservationById(id);
    }
}
