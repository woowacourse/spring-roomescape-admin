package roomescape.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.dto.ReservationTimeResponseDto;

@Service
@RequiredArgsConstructor
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public List<ReservationTimeResponseDto> findAll() {
        return reservationTimeDao.findAll().stream()
                .map(ReservationTimeResponseDto::from)
                .toList();
    }

    public ReservationTimeResponseDto create(ReservationTimeRequestDto requestDto) {
        ReservationTime saved = reservationTimeDao.save(requestDto.toEntity());
        return ReservationTimeResponseDto.from(saved);
    }

    public boolean delete(Long id) {
        return reservationTimeDao.deleteById(id) > 0;
    }
}
