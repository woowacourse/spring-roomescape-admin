package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationTimeCreateRequest;
import roomescape.controller.dto.ReservationTimeResponse;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(final ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTimeResponse createReservationTime(final ReservationTimeCreateRequest request) {
        final Long id = reservationTimeRepository.save(request);
        final ReservationTime reservationTime = request.toReservationTime(id);
        return new ReservationTimeResponse(reservationTime);
    }

    public List<ReservationTimeResponse> getAllReservationTimes() {
        return reservationTimeRepository.findAll()
                .stream()
                .map(ReservationTimeResponse::new)
                .collect(Collectors.toList());
    }

    public void cancelReservationTime(final Long id) {
        reservationTimeRepository.deleteById(id);
    }
}
