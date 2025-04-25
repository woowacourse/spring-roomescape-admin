package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationTimeCreateRequest;
import roomescape.dto.response.ReservationTimeResponse;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTimeResponse> findAll() {
        return reservationTimeRepository.findAll()
                .stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public ReservationTimeResponse findById(long id) {
        ReservationTime reservationTime = reservationTimeRepository.findById(id);

        return ReservationTimeResponse.from(reservationTime);
    }

    public ReservationTimeResponse save(ReservationTimeCreateRequest request) {
        ReservationTime saved = reservationTimeRepository.save(request.toReservationTime());

        return ReservationTimeResponse.from(saved);
    }

    public void deleteById(long id) {
        reservationTimeRepository.deleteById(id);
    }
}
