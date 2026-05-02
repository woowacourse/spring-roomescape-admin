package roomescape.service;

import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;

import java.util.List;

public interface ReservationService {

    Reservation add(ReservationRequest request);

    List<Reservation> findAllReservations();

    void deleteReservation(Long id);
}
