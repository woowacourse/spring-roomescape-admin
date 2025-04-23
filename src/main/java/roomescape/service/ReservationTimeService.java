package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.domain.exception.ReservationTimeException;
import roomescape.persist.repository.ReservationTimeRepository;
import roomescape.presentation.dto.ReservationTimeRequestDto;
import roomescape.presentation.dto.ReservationTimeResponseDto;

@Service
public final class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTimeResponseDto> getAllReservationTimes() {
        return reservationTimeRepository.findAll().stream()
                .map(reservationTime -> new ReservationTimeResponseDto(
                        reservationTime.getId(),
                        reservationTime.getStartTime()))
                .toList();
    }

    public ReservationTimeResponseDto addReservationTime(ReservationTimeRequestDto reservationTimeRequestDto) {
        if (reservationTimeRepository.existsByStartTime(reservationTimeRequestDto.startAt())) {
            throw new ReservationTimeException("예약 가능한 시간은 중복될 수 없습니다.");
        }
        ReservationTime reservationTime = reservationTimeRepository.add(
                new ReservationTime(reservationTimeRequestDto.startAt()));
        return new ReservationTimeResponseDto(reservationTime.getId(), reservationTime.getStartTime());
    }

    public void deleteReservationTime(long id) {
        reservationTimeRepository.removeById(id);
    }
}
