package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.util.List;

@Repository
public interface ReservationRepository {
    List<Reservation> findAllReservations();

    Reservation addReservation(Reservation reservation);

    void deleteById(Long id);
}
