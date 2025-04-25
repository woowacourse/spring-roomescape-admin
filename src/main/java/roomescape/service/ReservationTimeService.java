package roomescape.service;

import java.util.List;
import roomescape.entity.ReservationTime;
import roomescape.persistence.repository.reservationtime.ReservationTimeRepository;
import roomescape.presentation.dto.CreateReservationTimeDto;
import roomescape.presentation.dto.ReservationTimeResponseDto;

public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTimeResponseDto createReservationTime(CreateReservationTimeDto request) {
        Long id = reservationTimeRepository.addAndGetId(request);
        ReservationTime reservationTime = reservationTimeRepository.findById(id);
        return ReservationTimeResponseDto.from(reservationTime);
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
