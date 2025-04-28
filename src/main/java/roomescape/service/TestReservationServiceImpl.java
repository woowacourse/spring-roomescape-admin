package roomescape.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

public class TestReservationServiceImpl implements ReservationService {

    private final List<Reservation> reservations = new ArrayList<>();

    @Override
    public List<ReservationResponse> findAllReservations() {
        return reservations.stream()
                .map(ReservationResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public ReservationResponse createReservation(final ReservationRequest reservationRequest) {
        final Reservation reservation = reservationRequest.toEntity();
        reservations.add(reservation);
        reservation.setId(reservations.size() + 1L);
        return ReservationResponse.from(reservation);
    }

    @Override
    public int deleteReservationById(final Long id) {
        final int count = (int) reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .count();
        return count;
    }
}
