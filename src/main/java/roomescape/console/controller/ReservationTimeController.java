package roomescape.console.controller;

import org.springframework.stereotype.Controller;
import roomescape.console.controller.request.ReservationTimeRequest;
import roomescape.console.controller.response.ReservationTimeResponse;
import roomescape.core.domain.ReservationTime;
import roomescape.core.repository.ReservationTimeRepository;

import java.util.List;

@Controller
public class ReservationTimeController {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTimeResponse> findAll() {
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();

        return reservationTimes.stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public ReservationTimeResponse save(ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = reservationTimeRequest.toEntity();

        ReservationTime savedReservationTime = reservationTimeRepository.save(reservationTime);

        return ReservationTimeResponse.from(savedReservationTime);
    }

    public void delete(Long id) {
        reservationTimeRepository.deleteById(id);
    }
}
