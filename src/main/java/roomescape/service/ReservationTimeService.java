package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.dto.ReservationTimeResponseDto;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

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
