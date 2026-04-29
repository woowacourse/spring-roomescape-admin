package roomescape.service;

import java.util.List;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.service.dto.ReservationSaveServiceDto;

public interface RoomescapeService {
    List<Reservation> getReservations();
    Reservation save(ReservationSaveServiceDto reservation);
    boolean deleteById(long id);
    List<ReservationTime> getReservationTimes();
    ReservationTime saveReservationTime(ReservationTime reservationTime);
    boolean deleteReservationTimeById(long id);

}
