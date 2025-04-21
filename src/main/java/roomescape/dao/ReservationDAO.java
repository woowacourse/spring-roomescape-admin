package roomescape.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationDAO {

    List<Reservation> findAll();

    boolean existsByDateAndTime(LocalDate date, LocalTime time);

    long insert(Reservation reservation);

    boolean deleteById(long id);
}
