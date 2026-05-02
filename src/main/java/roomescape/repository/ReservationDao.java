package roomescape.repository;

import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationDao {

    List<ReservationJoinedDto> findAll();

    long insert(Reservation reservation);

    void deleteById(long reservationId);

    Reservation findById(long reservationId);

    ReservationJoinedDto findJoinedDtoById(long reservationId);
}
