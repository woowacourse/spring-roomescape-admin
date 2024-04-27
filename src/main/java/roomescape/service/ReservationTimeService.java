package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationTimeRequest;
import roomescape.controller.dto.ReservationTimeResponse;
import roomescape.domain.Time;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTimeResponse addReservationTime(ReservationTimeRequest reservationTimeRequest) {
        Time reservationTime = new Time(reservationTimeRequest.startAt());
        reservationTimeRepository.saveReservationTime(reservationTime);

        return new ReservationTimeResponse(reservationTime.getId(), reservationTime.getStartAt());
    }

    public List<ReservationTimeResponse> findReservationTimes() {
        List<Time> reservationTimes = reservationTimeRepository.findAllReservationTimes();

        return reservationTimes.stream()
                .map(reservationTime -> new ReservationTimeResponse(reservationTime.getId(),
                        reservationTime.getStartAt()))
                .toList();
    }

    public void removeReservationTime(long reservationTimeId) {
        reservationTimeRepository.deleteReservationTimeById(reservationTimeId);
    }
}
