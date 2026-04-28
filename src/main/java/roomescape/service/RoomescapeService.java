package roomescape.service;

import java.util.List;
import roomescape.domain.Reservation;
import roomescape.domain.dto.ReservationSaveRequestDto;

public interface RoomescapeService {
    List<Reservation> getReservations();
    Reservation save(ReservationSaveRequestDto reservation);
    boolean deleteById(long id);

}
