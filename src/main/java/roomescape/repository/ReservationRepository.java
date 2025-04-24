package roomescape.repository;

import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation save(final String name, final LocalDate requestDate, final Long timeId);

    void deleteById(final Long id);
}
