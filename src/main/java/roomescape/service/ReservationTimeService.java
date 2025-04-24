package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.entity.ReservationTimeEntity;

import java.util.List;

@Service
public class ReservationTimeService {
    private final ReservationTimeDao timeDao;

    public ReservationTimeService(ReservationTimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public ReservationTimeResponseDto create(ReservationTimeRequestDto requestDto) {
        ReservationTimeEntity saved = timeDao.save(requestDto.toEntity());
        return ReservationTimeResponseDto.from(saved);
    }

    public List<ReservationTimeResponseDto> getAllTimes() {
        return timeDao.findAll().stream()
                .map(ReservationTimeResponseDto::from)
                .toList();
    }

    public void delete(final Long id) {
        final int deleted = timeDao.deleteById(id);
        if (deleted == 0) {
            throw new IllegalArgumentException("존재하지 않는 id 입니다.");
        }
    }
}
