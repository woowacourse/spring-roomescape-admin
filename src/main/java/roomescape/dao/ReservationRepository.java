package roomescape.dao;

import java.util.List;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;

public interface ReservationRepository {
    List<Reservation> selectReservations();
    Reservation insertReservation(ReservationRequest request, ReservationTime time);
    void deleteReservation(Long id);
}
