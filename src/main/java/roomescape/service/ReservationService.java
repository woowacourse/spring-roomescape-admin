package roomescape.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.repository.ReservationRepository;

public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final AtomicLong index;

    public ReservationService() {
        this.reservationRepository = new ReservationRepository();
        this.index = new AtomicLong(1);
    }

    public List<ReservationResponse> read() {
        List<Reservation> reservations = reservationRepository.findAll();
        return ReservationResponse.fromReservations(reservations);
    }

    public ReservationResponse add(final ReservationRequest reservationRequest) {
        Long id = index.getAndIncrement(); // TODO: 여기 있어도 되는 로직일까?
        Reservation reservation = reservationRequest.toReservation(id);
        reservationRepository.save(reservation);
        return ReservationResponse.from(reservation);
    }

    public void remove(final Long id) {
        reservationRepository.delete(id);
    }
}
