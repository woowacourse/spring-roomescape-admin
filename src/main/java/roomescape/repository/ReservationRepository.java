package roomescape.repository;

import roomescape.entity.Reservation;

import java.util.List;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation findById(Long id);

    Reservation save(Reservation reservation);

    int deleteById(Long id);
}
