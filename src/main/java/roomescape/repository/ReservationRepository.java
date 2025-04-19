package roomescape.repository;

import roomescape.model.Reservation;

import java.util.List;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation findLast();

    void add(Reservation reservation);

    void deleteById(Long id);
}
