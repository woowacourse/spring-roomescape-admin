package roomescape.reservation.repository;

import java.util.List;
import roomescape.reservation.domain.Reservation;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation findById(Long id);

    Long save(Reservation reservation);

    void deleteById(Long id);
}
