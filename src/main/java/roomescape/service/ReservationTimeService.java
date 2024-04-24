package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeDto;
import roomescape.dto.ReservationTimeRequest;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTimeDto> findReservationTimes() {
        return reservationTimeRepository.findAll().stream()
                .map(ReservationTimeDto::from)
                .toList();
    }

    public ReservationTimeDto createReservationTime(ReservationTimeRequest reservationTimeRequest) {
        ReservationTime newReservationTime = reservationTimeRepository.save(reservationTimeRequest.toReservationTime());
        return ReservationTimeDto.from(newReservationTime);
    }

    public boolean deleteReservationTime(Long id) {
        try {
            reservationTimeRepository.deleteById(id);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
