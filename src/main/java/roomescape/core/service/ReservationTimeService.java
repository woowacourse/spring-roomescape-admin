package roomescape.core.service;

import org.springframework.stereotype.Service;
import roomescape.core.domain.ReservationTime;
import roomescape.core.repository.ReservationTimeRepository;
import roomescape.core.service.request.ReservationTimeRequestDto;
import roomescape.core.service.response.ReservationTimeResponseDto;

import java.util.List;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTimeResponseDto> findAll() {
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();

        return reservationTimes.stream()
                .map(ReservationTimeResponseDto::from)
                .toList();
    }

    public ReservationTimeResponseDto save(ReservationTimeRequestDto reservationTimeRequest) {
        ReservationTime reservationTime = reservationTimeRequest.toEntity();

        ReservationTime savedReservationTime = reservationTimeRepository.save(reservationTime);

        return ReservationTimeResponseDto.from(savedReservationTime);
    }

    public void deleteById(long id) {
        reservationTimeRepository.deleteById(id);
    }
}
