package roomescape.repository;

import java.util.List;
import roomescape.domain.Reservation;
import roomescape.service.dto.ReservationDto;

public interface ReservationRepository {

    Reservation addReservation(ReservationDto reservationDto);

    List<Reservation> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    void deleteAll();
}
