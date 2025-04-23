package roomescape.reservation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.model.Reservation;
import roomescape.reservation.model.ReservationDetails;
import roomescape.reservation.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    public Reservation addReservation(ReservationRequest request) {
        ReservationDetails reservationDetails = new ReservationDetails(request.name(), request.date(), request.time());
        return reservationRepository.insertReservation(reservationDetails);
    }

    public boolean deleteReservationById(long id) {
        return reservationRepository.deleteReservationById(id);
    }
}
