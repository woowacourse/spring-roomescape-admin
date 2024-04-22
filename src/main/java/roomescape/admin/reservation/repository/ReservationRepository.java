package roomescape.admin.reservation.repository;

import java.util.List;
import roomescape.admin.reservation.entity.Reservation;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation save(Reservation reservation);

    int delete(Long id);
}
