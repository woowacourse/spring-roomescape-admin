package roomescape.reservation.repository;

import java.util.List;
import roomescape.reservation.entity.Reservation;
import roomescape.reservation.payload.ReservationRequest;
import roomescape.reservation.payload.ReservationWithTimeResponse;

public interface ReservationRepository {

    Reservation save(ReservationRequest request);

    List<ReservationWithTimeResponse> findAll();

    void deleteById(Long id);

}
