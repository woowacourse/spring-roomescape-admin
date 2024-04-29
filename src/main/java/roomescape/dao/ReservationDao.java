package roomescape.dao;

import roomescape.dto.ReservationSaveDto;
import roomescape.entity.Reservation;

import java.util.List;

public interface ReservationDao {

    List<Reservation> findAll();

    Reservation findById(long id);

    long save(ReservationSaveDto reservationSaveDto);

    void delete(long id);
}
