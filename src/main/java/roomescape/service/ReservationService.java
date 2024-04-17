package roomescape.service;

import java.util.List;
import roomescape.db.domain.Reservation;
import roomescape.web.dto.ReservationRequest;
import roomescape.web.dto.ReservationResponse;
import roomescape.db.repository.InMemoryReservationRepository;
import roomescape.db.repository.ReservationRepository;

public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService() {
        this.reservationRepository = new InMemoryReservationRepository();
    }

    public List<ReservationResponse> read() {
        List<Reservation> reservations = reservationRepository.findAll();
        return ReservationResponse.fromReservations(reservations);
    }

    public ReservationResponse add(final ReservationRequest reservationRequest) {
        long id = reservationRepository.getCurrentId();
        Reservation reservation = reservationRequest.toReservation(id);

        reservationRepository.save(reservation);
        return ReservationResponse.from(reservation);
    }

    public void remove(final long id) {
        reservationRepository.delete(id);
    }
}
