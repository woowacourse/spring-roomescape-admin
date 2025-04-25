package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(final ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTimeResponse> getAllReservationTimes() {
        return reservationTimeRepository.findAll().stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public ReservationTimeResponse createReservationTime(final ReservationTimeRequest reservationTimeRequest) {
        Long id = reservationTimeRepository.add(reservationTimeRequest.toReservationTime());
        ReservationTime addedReservationTime = reservationTimeRepository.findById(id);
        return ReservationTimeResponse.from(addedReservationTime);
    }

    public void deleteReservationTime(final Long id) {
        reservationTimeRepository.removeById(id);
    }
}
