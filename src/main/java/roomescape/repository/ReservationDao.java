package roomescape.repository;

import roomescape.model.Reservation;
import roomescape.repository.dto.ReservationSaveDto;

import java.util.List;

public interface ReservationDao {

    Reservation save(final ReservationSaveDto reservationSaveDto);

    List<Reservation> findAll();

    boolean deleteById(final Long id);
}
