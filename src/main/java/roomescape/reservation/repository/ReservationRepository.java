package roomescape.reservation.repository;

import java.util.List;
import roomescape.reservation.domain.Reservation;

public interface ReservationRepository {
    List<Reservation> findAll();

    Long save(Reservation reservation);

    void delete(Long id);

    Boolean existsByDateAndTime(String date, Long timeId);
}
