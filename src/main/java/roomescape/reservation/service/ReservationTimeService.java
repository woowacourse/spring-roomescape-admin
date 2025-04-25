package roomescape.reservation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservation.dto.ReservationTimeRequest;
import roomescape.reservation.dto.ReservationTimeResponse;
import roomescape.reservation.entity.ReservationTime;
import roomescape.reservation.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTimeResponse> getAll() {
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();

        return reservationTimes.stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public ReservationTimeResponse save(ReservationTimeRequest request) {
        ReservationTime reservationTime = ReservationTime.withoutId(request.startAt());

        ReservationTime saved = reservationTimeRepository.save(reservationTime);

        return ReservationTimeResponse.from(saved);
    }

    public void delete(Long id){
        reservationTimeRepository.deleteById(id);
    }
}
