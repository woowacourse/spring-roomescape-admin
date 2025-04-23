package roomescape.repository;

import roomescape.entity.Reservation;

import java.util.List;

public interface ReservationRepository {

    Long add(final Reservation reservation);

    Reservation findById(final Long id);

    void deleteById(final Long id);

    List<Reservation> findAll();
}
