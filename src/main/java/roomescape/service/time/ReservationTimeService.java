package roomescape.service.time;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.repository.time.ReservationTimeRepository;
import roomescape.service.time.dto.ReservationTimeRequestDto;
import roomescape.service.time.dto.ReservationTimeResponseDto;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTimeResponseDto> findAllReservationTimes() {
        return reservationTimeRepository.findAllReservationTimes()
                .stream()
                .map(ReservationTimeResponseDto::new)
                .toList();
    }

    public ReservationTimeResponseDto createReservationTime(ReservationTimeRequestDto requestDto) {
        ReservationTime time = reservationTimeRepository.insertReservationTime(requestDto.toReservationTime());
        return new ReservationTimeResponseDto(time);
    }

    public void deleteReservationTime(long id) {
        reservationTimeRepository.deleteReservationTimeById(id);
    }
}
