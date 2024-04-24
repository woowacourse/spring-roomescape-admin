package roomescape.admin.reservation.service.port;

import java.util.List;
import roomescape.admin.reservation.entity.Reservation;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation save(Reservation reservation);

    int delete(Long id);
}
