package roomescape.repository;

import roomescape.domain.Reservation;

import java.util.List;

public interface ReservationRepository {

    Reservation insert(Reservation reservation);

    List<Reservation> findAll();

    int deleteById(Long id);
}
