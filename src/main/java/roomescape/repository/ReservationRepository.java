package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import roomescape.Reservation;

@Repository
public interface ReservationRepository {

    List<Reservation> findAll();

    boolean existsByDateAndTime(final LocalDate date, final LocalTime time);

    Reservation save(final String name, final LocalDate date, final Long timeId, final LocalTime time);

    Optional<Reservation> findById(final Long id);

    void remove(final Long id);
}
