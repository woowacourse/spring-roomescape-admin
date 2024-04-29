package roomescape.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Optional<Reservation> findById(long id);

    List<Reservation> findAll();

    Reservation save(Reservation reservation);

    void deleteById(long id);

    long findReservationCountByTimeId(long timeId);

    boolean existByNameAndDateAndTimeId(String name, LocalDate date, long timeId);
}
