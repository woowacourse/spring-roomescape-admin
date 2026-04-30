package roomescape.reservation.repository;

import java.util.List;
import roomescape.reservation.entity.Reservation;
import roomescape.reservation.payload.ReservationRequest;

public interface ReservationRepository {

    Reservation save(ReservationRequest request);

    List<Reservation> findAll();

    void deleteById(Long id);

}
