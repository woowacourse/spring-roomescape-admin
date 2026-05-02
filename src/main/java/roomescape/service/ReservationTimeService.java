package roomescape.service;

import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dto.ReservationResponseDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.entity.ReservationTime;
import roomescape.exception.ErrorCode;
import roomescape.exception.ReservationTimeException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(
        final ReservationRepository reservationRepository,
        final ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
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
        if (reservationRepository.existsByTimeId(id)) {
            throw new ReservationTimeException(ErrorCode.RESERVATION_TIME_IS_REFERENCED);
        }
        reservationTimeRepository.deleteById(id);
    }
}
