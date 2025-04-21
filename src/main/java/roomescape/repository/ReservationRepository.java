package roomescape.repository;

import java.util.List;
import roomescape.Reservation;

public interface ReservationRepository {

    List<Reservation> findAll();
}
