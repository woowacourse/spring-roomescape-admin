package roomescape.dao;

import java.util.List;
import roomescape.domain.Reservation;
import roomescape.service.dto.ReservationCreationDto;

public interface ReservationDao {
    List<Reservation> findAll();

    Reservation add(ReservationCreationDto request);

    void delete(Long id);

    boolean isExist(Long id);

    boolean isExistByTimeId(Long timeId);
}
