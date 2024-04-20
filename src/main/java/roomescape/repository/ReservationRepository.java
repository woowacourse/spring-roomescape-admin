package roomescape.repository;

import java.util.List;
import roomescape.domain.Reservation;
import roomescape.service.dto.ReservationCreationDto;

public interface ReservationRepository {

    Reservation addReservation(ReservationCreationDto reservationCreationDto);

    List<Reservation> findAll();

    void deleteById(Long id);

    void deleteAll();
}
