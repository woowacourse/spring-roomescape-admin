package roomescape.repository;

import roomescape.dto.ReservationTimeDto;

import java.util.List;

public interface ReservationTimeRepository {

    ReservationTimeDto add(ReservationTimeDto reservationTimeDto);
    ReservationTimeDto findById(Long id);
    List<ReservationTimeDto> findAll();
    int remove(Long id);
}
