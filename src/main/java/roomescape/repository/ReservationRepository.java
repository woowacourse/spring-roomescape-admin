package roomescape.repository;

import roomescape.dto.ReservationRepositoryDto;

import java.util.List;

public interface ReservationRepository {

    ReservationRepositoryDto add(ReservationRepositoryDto reservationRepositoryDto);
    List<ReservationRepositoryDto> findAll();
    boolean remove(Long id);
}
