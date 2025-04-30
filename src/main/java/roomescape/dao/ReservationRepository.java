package roomescape.dao;

import java.time.LocalDate;
import java.util.List;
import roomescape.model.Reservation;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation addAndGet(String name, LocalDate date, long timeId);

    int deleteById(Long id);
}
