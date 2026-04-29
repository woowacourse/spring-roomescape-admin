package roomescape.domain.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import roomescape.domain.entity.ReservationTime;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTimeResponseDto postReservationTime(ReservationTimeRequestDto reservationTimeRequestDto) {
        ReservationTime reservationTime = new ReservationTime(reservationTimeRequestDto.startAt());
        Long id = reservationTimeRepository.save(reservationTime);
        return new ReservationTimeResponseDto(id, reservationTime.getStartAt());
    }

    public List<ReservationTime> getAllReservationTime() {
        return reservationTimeRepository.findAll();
    }

    public void deleteReservationTimeBy(Long id) {
        reservationTimeRepository.delete(id);
    }
}
