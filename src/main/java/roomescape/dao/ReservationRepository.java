package roomescape.dao;

import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation findById(Long id);

    Reservation save(Reservation reservation);

    void deleteById(Long id);
}
