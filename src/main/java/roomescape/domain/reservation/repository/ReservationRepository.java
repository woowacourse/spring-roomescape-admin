package roomescape.domain.reservation.repository;

import java.util.List;
import roomescape.domain.reservation.Reservation;
import roomescape.dto.ReservationDto;

public interface ReservationRepository {
    Reservation save(ReservationDto reservationDto);

    List<Reservation> findAll();

    void deleteById(Long id);
}
