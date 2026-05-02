package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.controller.ReservationRequest;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> allReservations() {
        return reservationRepository.findAll();
    }

    public long saveReservation(ReservationRequest reservationRequest) {
        ReservationCommand reservationCommand = new ReservationCommand(reservationRequest.name(), reservationRequest.date(), reservationRequest.timeId());
        return reservationRepository.save(reservationCommand);
    }

    public void removeReservation(long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    public Reservation findReservation(long reservationId) {
        return reservationRepository.findById(reservationId);
    }
}
