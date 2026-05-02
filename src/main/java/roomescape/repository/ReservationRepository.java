package roomescape.repository;

import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> findAll();

    Reservation save(String name, String date, ReservationTime time);

    void deleteById(Long id);
}
