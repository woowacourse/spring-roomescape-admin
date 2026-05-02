package roomescape.repository;

import java.util.List;
import roomescape.domain.Reservation;
import roomescape.controller.ReservationRequest;
import roomescape.service.ReservationCommand;

public interface ReservationDao {

    List<Reservation> findAll();

    long insert(ReservationCommand reservationCommand);

    void deleteById(long reservationId);

    Reservation findById(long reservationId);
}
