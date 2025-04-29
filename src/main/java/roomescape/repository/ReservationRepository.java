package roomescape.repository;

import java.util.List;
import roomescape.model.Reservation;

public interface ReservationRepository {
    List<Reservation> findAll();

    void deleteById(Long id);

    Reservation save(Reservation reservation);
}
