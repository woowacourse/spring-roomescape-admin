package roomescape.Time.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.Time.controller.dto.ReservationTimeRequest;
import roomescape.Time.controller.dto.ReservationTimeResponse;
import roomescape.Time.domain.Time;
import roomescape.Time.repository.TimeRepository;

@Service
public class TimeService {
    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public ReservationTimeResponse addReservationTime(ReservationTimeRequest reservationTimeRequest) {
        Time reservationTime = new Time(reservationTimeRequest.startAt());
        timeRepository.saveReservationTime(reservationTime);

        return new ReservationTimeResponse(reservationTime.getId(), reservationTime.getStartAt());
    }

    public List<ReservationTimeResponse> findReservationTimes() {
        List<Time> reservationTimes = timeRepository.findAllReservationTimes();

        return reservationTimes.stream()
                .map(reservationTime -> new ReservationTimeResponse(reservationTime.getId(),
                        reservationTime.getStartAt()))
                .toList();
    }

    public void removeReservationTime(long reservationTimeId) {
        timeRepository.deleteReservationTimeById(reservationTimeId);
    }
}
