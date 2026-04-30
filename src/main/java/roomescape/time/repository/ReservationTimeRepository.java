package roomescape.time.repository;

import roomescape.time.dto.TimeRequestDto;
import roomescape.time.entity.ReservationTime;

import java.util.List;

public interface ReservationTimeRepository {

    ReservationTime save(TimeRequestDto requestDto);

    void delete(Long id);

    ReservationTime findById(Long id);

    List<ReservationTime> findAll();
}
