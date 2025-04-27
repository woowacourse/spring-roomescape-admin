package roomescape.service;

import java.util.List;

import org.springframework.stereotype.Service;

import roomescape.dto.request.ReservationTimeRequest;
import roomescape.dto.response.ReservationTimeResponse;
import roomescape.model.ReservationTime;
import roomescape.repository.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTimeResponse> getAll() {
        return reservationTimeRepository.getAll().stream()
            .map(ReservationTimeResponse::from)
            .toList();
    }

    public ReservationTimeResponse create(ReservationTimeRequest request) {
        var saved = reservationTimeRepository.save(new ReservationTime(request.id(), request.startAt()));
        return ReservationTimeResponse.from(saved);
    }

    public void remove(Long id) {
        reservationTimeRepository.remove(id);
    }
}
