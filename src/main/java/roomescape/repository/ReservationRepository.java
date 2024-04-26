package roomescape.repository;

import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationRepository {
    Reservation save(Reservation reservationRequest);

    List<Reservation> findAll();

    void deleteById(Long id);
}
