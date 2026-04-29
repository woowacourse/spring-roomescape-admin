package roomescape.dao;

import roomescape.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationDao {

    Reservation select(Long id);

    List<Reservation> selectAll();

    Reservation insert(String name, LocalDate date, LocalTime time);

    void delete(Long id);

}
