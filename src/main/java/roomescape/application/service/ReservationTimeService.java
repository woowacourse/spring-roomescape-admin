package roomescape.application.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.application.dto.ReservationTimeRequest;
import roomescape.application.dto.ReservationTimeResponse;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimes;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTimeResponse> getReservationTimes() {
        ReservationTimes reservationTimes = reservationTimeRepository.findAll();
        return reservationTimes.getReservationTimes().stream()
                .map(ReservationTimeResponse::new)
                .toList();
    }

    public ReservationTimeResponse saveReservationTime(ReservationTimeRequest request) {
        ReservationTime newReservationTime = request.toReservationTime(null);
        ReservationTime savedReservationTime = reservationTimeRepository.save(newReservationTime);

        return new ReservationTimeResponse(savedReservationTime);
    }

    public boolean deleteReservationTime(Long id) {
        return reservationTimeRepository.deleteById(id);
    }
}
