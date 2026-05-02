package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.controller.ReservationRequest;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationJoinedDto;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationJoinedDto> allReservations() {
        return reservationRepository.findAll();
    }

    public ReservationJoinedDto saveReservation(ReservationRequest reservationRequest) {
        Reservation transientReservation = Reservation.transientOf(reservationRequest);
        long reservationId = reservationRepository.save(transientReservation);
        return reservationRepository.findJoinedDtoById(reservationId);
    }

    public void removeReservation(long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    public Reservation findReservation(long reservationId) {
        return reservationRepository.findById(reservationId);
    }
}
