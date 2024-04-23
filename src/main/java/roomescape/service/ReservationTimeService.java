package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.dto.ReservationTimeSaveDto;
import roomescape.entity.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    @Autowired
    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTimeResponseDto> findTimes() {
        List<ReservationTime> times = reservationTimeRepository.findAll();
        return times.stream()
                .map(ReservationTimeResponseDto::from)
                .toList();
    }

    public ReservationTimeResponseDto addTime(ReservationTimeRequestDto reservationTimeDto) {
        ReservationTime reservationTime = reservationTimeRepository.save(ReservationTimeSaveDto.from(reservationTimeDto));
        return ReservationTimeResponseDto.from(reservationTime);
    }

    public void deleteTime(long id) {
        reservationTimeRepository.delete(id);
    }
}
