package roomescape.repository;

import roomescape.dto.ReservationTimeResponseDto;

import java.time.LocalTime;
import java.util.List;

public interface ReservationTimeRepository {

    public Long saveAndReturnId(final LocalTime startAt);

    public List<ReservationTimeResponseDto> findAll();

    public void deleteById(Long id);
}
