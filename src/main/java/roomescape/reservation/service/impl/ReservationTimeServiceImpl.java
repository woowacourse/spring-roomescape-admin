package roomescape.reservation.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservation.dto.ReservationTimeRequestDto;
import roomescape.reservation.dto.ReservationTimeResponseDto;
import roomescape.reservation.entity.ReservationTime;
import roomescape.reservation.repository.ReservationTimeRepository;
import roomescape.reservation.service.ReservationTimeService;

@Service
public class ReservationTimeServiceImpl implements ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeServiceImpl(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @Override
    public List<ReservationTimeResponseDto> getAll() {
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();

        return reservationTimes.stream()
                .map(ReservationTimeResponseDto::from)
                .toList();
    }

    @Override
    public ReservationTimeResponseDto save(ReservationTimeRequestDto requestDto) {
        ReservationTime reservationTime = ReservationTime.withoutId(requestDto.startAt());

        ReservationTime saved = reservationTimeRepository.save(reservationTime);

        return ReservationTimeResponseDto.from(saved);
    }

    @Override
    public void delete(Long id){
        reservationTimeRepository.deleteById(id);
    }
}
