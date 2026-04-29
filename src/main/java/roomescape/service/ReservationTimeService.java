package roomescape.service;

import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.entity.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@Service
@RequiredArgsConstructor
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public List<ReservationTimeResponseDto> getAllReservationTimes() {
        return reservationTimeRepository.findAll()
            .stream()
            .map(ReservationTimeResponseDto::from)
            .toList();
    }

    public ReservationTimeResponseDto createReservationTime(final LocalTime startAt) {
        final ReservationTime reservationTime = ReservationTime.builder()
            .startAt(startAt)
            .build();

        return ReservationTimeResponseDto.from(reservationTimeRepository.save(reservationTime));
    }

    public void removeReservationTime(final long id) {
        reservationTimeRepository.deleteById(id);
    }
}
