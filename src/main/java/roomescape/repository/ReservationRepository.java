package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.entity.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Reservation findById(long id);

    List<Reservation> findAll();

    Reservation save(Reservation reservation);

    void deleteById(long id);

    boolean selectByDateAndTime(LocalDate date, LocalTime time);
}
