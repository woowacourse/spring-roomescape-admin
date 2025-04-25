package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(final ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTimeResponseDto> getAllReservationTimes() {
        return reservationTimeRepository.findAll().stream()
                .map(ReservationTimeResponseDto::from)
                .toList();
    }

    public ReservationTimeResponseDto createReservationTime(final ReservationTimeRequestDto reservationTimeRequestDto) {
        Long id = reservationTimeRepository.add(reservationTimeRequestDto.toReservationTime());
        ReservationTime reservationTime = reservationTimeRepository.findById(id);
        return ReservationTimeResponseDto.from(reservationTime);
    }

    public void deleteReservationTime(final Long id) {
        reservationTimeRepository.removeById(id);
    }
}
