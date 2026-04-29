package roomescape.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeCreateDto;
import roomescape.dto.ReservationTimeDto;
import roomescape.repository.ReservationTimeRepository;

@RequiredArgsConstructor
@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

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
