package roomescape.repository;

import java.util.List;
import java.util.Optional;
import roomescape.model.Reservation;
import roomescape.repository.dto.SaveReservationDto;

public interface ReservationRepository {

    Optional<Reservation> findById(long id);

    long save(SaveReservationDto dto);

    boolean removeById(long id);

    List<Reservation> getReservations();
}
