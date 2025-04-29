package roomescape.repository;

import roomescape.entity.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Long add(final Reservation reservation);

    Optional<Reservation> findById(final Long id);

    void deleteById(final Long id);

    List<Reservation> findAll();
}
