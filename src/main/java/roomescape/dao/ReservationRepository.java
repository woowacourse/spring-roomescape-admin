package roomescape.dao;

import roomescape.domain.Reservation;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> findAll();

    Reservation save(Reservation reservation);

    boolean existsById(long id);

    void deleteById(long id);
}
