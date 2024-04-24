package roomescape.reservationtime.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservationtime.controller.request.CreateReservationTimeRequest;
import roomescape.reservationtime.controller.response.FindReservationTimeResponse;
import roomescape.reservationtime.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(final ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<FindReservationTimeResponse> getReservationTimes() {
        return reservationTimeRepository.findAll().stream()
                .map(FindReservationTimeResponse::of)
                .toList();
    }

    public FindReservationTimeResponse getReservationTime(final Long id) {
        return FindReservationTimeResponse.of(reservationTimeRepository.findById(id));
    }

    public Long createReservationTime(final CreateReservationTimeRequest createReservationTimeRequest) {
        return reservationTimeRepository.save(createReservationTimeRequest.toReservationTime());
    }

    public void deleteById(final Long id) {
        reservationTimeRepository.deleteById(id);
    }
}
