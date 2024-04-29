package roomescape.repository;

import roomescape.dto.ReservationRepositoryTimeDto;

import java.util.List;

public interface ReservationTimeRepository {

    ReservationRepositoryTimeDto add(ReservationRepositoryTimeDto reservationRepositoryTimeDto);
    ReservationRepositoryTimeDto findById(Long id);
    List<ReservationRepositoryTimeDto> findAll();
    boolean remove(Long id);
}
