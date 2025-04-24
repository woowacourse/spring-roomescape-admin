package roomescape.repositiory;

import java.util.List;
import roomescape.domain.Reservation;
import roomescape.domain.dto.ReservationRequestDto;

public interface ReservationRepository {

    List<Reservation> findAll();

    Reservation findById(Long id);

    Long add(ReservationRequestDto reservation);

    void delete(Long id);
}
