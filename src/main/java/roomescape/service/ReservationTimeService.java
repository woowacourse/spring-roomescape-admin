package roomescape.service;

import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.entity.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(final ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @Transactional
    public List<ReservationTimeResponseDto> getAllReservationTimes() {
        return reservationTimeRepository.findAll()
            .stream()
            .map(ReservationTimeResponseDto::from)
            .toList();
    }

    @Transactional
    public ReservationTimeResponseDto createReservationTime(final LocalTime startAt) {
        final ReservationTime reservationTime = ReservationTime.builder()
            .startAt(startAt)
            .build();

        return ReservationTimeResponseDto.from(reservationTimeRepository.save(reservationTime));
    }

    @Transactional
    public void removeReservationTime(final long id) {
        reservationTimeRepository.deleteById(id);
    }
}
