package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import roomescape.entity.Reservation;

public interface ReservationRepository {

    Reservation findById(long id);

    List<Reservation> findAll();

    Reservation save(Reservation reservation);

    int deleteById(long id);

    boolean selectByDateAndTime(LocalDate date, LocalTime time);
}
