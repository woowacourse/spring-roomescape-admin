package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.exception.reservationTime.ReservationTimeNotFoundException;
import roomescape.repository.reservationTime.ReservationTimeRepository;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository timeRepository;

    public ReservationTimeService(ReservationTimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public ReservationTimeResponse create(ReservationTimeRequest request) {
        ReservationTime newReservationTime = new ReservationTime(request.getStartAt());
        return ReservationTimeResponse.from(timeRepository.add(newReservationTime));
    }

    public List<ReservationTimeResponse> getAll() {
        return ReservationTimeResponse.from(timeRepository.findAll());
    }

    public void deleteBy(Long id) {
        int affectedCount = timeRepository.deleteBy(id);
        if (affectedCount == 0) {
            throw new ReservationTimeNotFoundException(id);
        }
    }

    public ReservationTime getBy(Long id) {
        return timeRepository.findBy(id)
                .orElseThrow(() -> new ReservationTimeNotFoundException(id));
    }
}
