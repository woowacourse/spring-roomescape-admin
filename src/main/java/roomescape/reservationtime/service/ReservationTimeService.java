package roomescape.reservationtime.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.reservationtime.domain.ReservationTime;
import roomescape.reservationtime.dto.ReservationTimeCreateDto;
import roomescape.reservationtime.dto.ReservationTimeDto;
import roomescape.reservationtime.exception.ReservationTimeException;
import roomescape.reservationtime.repository.ReservationTimeRepository;

@RequiredArgsConstructor
@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTime findById(Long reservationTimeId) {
        return reservationTimeRepository.findById(reservationTimeId)
                .orElseThrow(() -> new ReservationTimeException("[ERROR] 존재하지 않는 timeId 입니다."));
    }

    public List<ReservationTimeDto> findAllReservationTimes() {
        List<ReservationTime> result = reservationTimeRepository.findAll();

        return result.stream()
                .map(ReservationTimeDto::from)
                .toList();
    }

    public ReservationTimeDto saveReservationTime(ReservationTimeCreateDto request) {
        ReservationTime reservationTime = request.toEntity();

        Long saveId = reservationTimeRepository.save(reservationTime);
        ReservationTime saved = ReservationTime.builder()
                .id(saveId)
                .startAt(reservationTime.getStartAt())
                .build();

        return ReservationTimeDto.from(saved);
    }

    public void deleteReservationTime(Long id) {
        reservationTimeRepository.delete(id);
    }
}
