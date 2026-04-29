package roomescape.dao;

import java.util.List;
import roomescape.domain.dto.ReservationTimeRequestDto;
import roomescape.domain.entity.ReservationTime;

public interface ReservationTimeDao {
    ReservationTime create(ReservationTimeRequestDto requestDto);

    List<ReservationTime> readAll();

    void delete(Long id);
}
