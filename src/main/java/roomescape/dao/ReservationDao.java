package roomescape.dao;

import java.util.List;
import roomescape.domain.dto.ReservationRequestDto;
import roomescape.domain.entity.Reservation;

public interface ReservationDao {
    Reservation create(ReservationRequestDto requestDto);

    List<Reservation> readAll();

    void delete(Long id);
}
