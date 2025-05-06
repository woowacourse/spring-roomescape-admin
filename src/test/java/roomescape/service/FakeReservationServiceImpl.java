package roomescape.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

public class FakeReservationServiceImpl implements ReservationService {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong atomicLong = new AtomicLong(1L);

    @Override
    public List<ReservationResponse> findAllReservations() {
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @Override
    public ReservationResponse createReservation(final ReservationRequest reservationRequest) {
        final Reservation reservation = reservationRequest.toEntity();
        reservation.setId(atomicLong.getAndIncrement());
        reservations.add(reservation);
        return ReservationResponse.from(reservation);
    }

    @Override
    public int deleteReservationById(final Long id) {
        int beforeSize = reservations.size();
        reservations.removeIf(reservation -> reservation.getId()
                .equals(id));
        int afterSize = reservations.size();
        return beforeSize - afterSize;
    }

    @Override
    public boolean existsById(final Long id) {
        return reservations.stream()
                .anyMatch(reservation -> reservation.getId()
                        .equals(id));
    }
}
