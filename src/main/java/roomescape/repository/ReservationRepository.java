package roomescape.repository;

import roomescape.domain.entity.Reservation;

import java.util.List;

public interface ReservationRepository {

    Long save(Reservation reservation);
    List<Reservation> findAll();
    void delete(Long id);
}
