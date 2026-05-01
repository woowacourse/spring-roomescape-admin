package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dto.ReservationTimeResponse;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @Transactional
    public ReservationTimeResponse create(ReservationTime reservationTime) {
        Long id = reservationTimeRepository.create(reservationTime);
        ReservationTime newReservation = new ReservationTime(id, reservationTime.getStartTime());
        return ReservationTimeResponse.of(newReservation);
    }

    @Transactional
    public List<ReservationTimeResponse> findAll() {
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();
        return reservationTimes.stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public int delete(Long id) {
        return reservationTimeRepository.delete(id);
    }
}
