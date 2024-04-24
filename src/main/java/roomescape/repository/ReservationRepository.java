package roomescape.repository;

import roomescape.model.Reservation;

import java.util.List;

public interface ReservationRepository {

    List<Reservation> findAll();

    List<Reservation> findAllByReservationTimeId(Long id);

    Reservation findById(Long id);

    Reservation save(Reservation reservation);

    void deleteById(Long id);
}
