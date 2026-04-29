package roomescape.reservation.service;

import java.util.List;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.ReservationTime;
import roomescape.reservation.service.dto.ReservationSaveServiceDto;

public interface RoomescapeService {
    List<Reservation> getReservations();
    Reservation save(ReservationSaveServiceDto reservation);
    boolean deleteById(long id);
    List<ReservationTime> getReservationTimes();
    ReservationTime saveReservationTime(ReservationTime reservationTime);
    boolean deleteReservationTimeById(long id);

}
