package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.Reservation;

import java.util.List;

@Repository
public interface ReservationRepository {
    List<Reservation> findAll();

    Reservation add(Reservation reservation);

    void delete(Long id);

    List<Reservation> findAllByDateTime(Reservation reservation);
}
