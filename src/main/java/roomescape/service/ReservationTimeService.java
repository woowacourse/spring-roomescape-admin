package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dto.ReservationTimeRequest;
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
    public ReservationTimeResponse create(ReservationTimeRequest request) {
        ReservationTime reservationTime = new ReservationTime(request.getStartAt());
        Long id = reservationTimeRepository.create(reservationTime);
        ReservationTime savedReservationTime = new ReservationTime(id, reservationTime.getStartTime());
        return ReservationTimeResponse.of(savedReservationTime);
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
