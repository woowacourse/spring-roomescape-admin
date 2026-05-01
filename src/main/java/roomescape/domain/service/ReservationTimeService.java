package roomescape.domain.service;

import org.springframework.stereotype.Service;
import roomescape.domain.entity.ReservationTime;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTimeResponseDto postReservationTime(ReservationTimeRequestDto reservationTimeRequestDto) {
        ReservationTime reservationTime = new ReservationTime(reservationTimeRequestDto.startAt());
        validateIsDuplicated(reservationTime);

        Long id = reservationTimeRepository.save(reservationTime);
        return new ReservationTimeResponseDto(id, reservationTime.getStartAt());
    }

    private void validateIsDuplicated(ReservationTime reservationTime) {
        boolean isDuplicated = reservationTimeRepository.findAll().stream()
                .anyMatch(time -> time.isSameReservationTime(reservationTime));

        if (isDuplicated) {
            throw new IllegalArgumentException("시간이 존재합니다.");
        }
    }

    public List<ReservationTime> getAllReservationTime() {
        return reservationTimeRepository.findAll();
    }

    public void deleteReservationTimeBy(Long id) {
        reservationTimeRepository.delete(id);
    }
}
