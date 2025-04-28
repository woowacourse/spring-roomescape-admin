package roomescape.repository;

import roomescape.model.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository {

    List<Reservation> findAll();

    boolean existByDateAndTimeId(LocalDate date, Long timeId);

    Reservation insert(Reservation reservation);

    int deleteById(Long id);
}
