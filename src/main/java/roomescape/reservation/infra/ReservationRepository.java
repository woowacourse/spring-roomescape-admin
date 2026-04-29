package roomescape.reservation.infra;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import roomescape.reservation.domain.Reservation;

public interface ReservationRepository {
    Reservation save(String name, LocalDate date, LocalTime time);

    List<Reservation> findAll();

    void deleteById(Long id);
}
