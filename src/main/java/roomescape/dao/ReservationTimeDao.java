package roomescape.dao;

import java.util.List;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequestDto;

public interface ReservationTimeDao {
    ReservationTime create(ReservationTimeRequestDto requestDto);

    List<ReservationTime> readAll();

    void delete(Long id);
}
