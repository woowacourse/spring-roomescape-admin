package roomescape.service;

import java.time.LocalTime;
import java.util.List;
import roomescape.entity.ReservationTime;
import roomescape.persistence.repository.reservationtime.ReservationTimeRepository;
import roomescape.presentation.dto.CreateReservationTimeDto;
import roomescape.presentation.dto.ReservationTimeResponseDto;
import roomescape.util.DateTimeFormatUtils;

public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTimeResponseDto createReservationTime(CreateReservationTimeDto request) {
        LocalTime startAt = DateTimeFormatUtils.formatTimeFrom(request.startAt());
        ReservationTime reservationTime = new ReservationTime(startAt);

        Long id = reservationTimeRepository.addAndGetId(reservationTime);
        return ReservationTimeResponseDto.fromIdAndReservationTime(id, reservationTime);
    }

    public List<ReservationTimeResponseDto> getAllReservationTimes() {
        return reservationTimeRepository.findAll().stream()
                .map(ReservationTimeResponseDto::from)
                .toList();
    }

    public void deleteReservationTime(Long id) {
        reservationTimeRepository.deleteById(id);
    }
}
