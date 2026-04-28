package roomescape.service;

import java.util.List;
import roomescape.domain.Reservation;
import roomescape.service.dto.ReservationSaveServiceDto;

public interface RoomescapeService {
    List<Reservation> getReservations();
    Reservation save(ReservationSaveServiceDto reservation);
    boolean deleteById(long id);

}
