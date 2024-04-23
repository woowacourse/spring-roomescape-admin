package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimeRepository;
import roomescape.dto.ReservationTimeCreateDto;
import roomescape.dto.ReservationTimeResponseDto;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTimeResponseDto> getAllReservationTimes() {
        return reservationTimeRepository.findAll().stream()
                .map(ReservationTimeResponseDto::from)
                .toList();
    }

    @Transactional
    public ReservationTimeResponseDto createReservationTime(ReservationTimeCreateDto createDto) {
        ReservationTime time = createDto.toDomain();
        ReservationTime createdReservationTime = reservationTimeRepository.create(time);
        return ReservationTimeResponseDto.from(createdReservationTime);
    }

    public void deleteReservationTime(Long id) {
        reservationTimeRepository.removeById(id);
    }
}
