package roomescape.reservation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservation.dto.ReservationTimeRequest;
import roomescape.reservation.dto.ReservationTimeResponse;
import roomescape.reservation.model.ReservationTime;
import roomescape.reservation.model.ReservationTimeDetails;
import roomescape.reservation.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTimeResponse addTime(ReservationTimeRequest reservationTimeRequest) {
        ReservationTimeDetails reservationTimeDetails = new ReservationTimeDetails(reservationTimeRequest.startAt());
        ReservationTime reservationTime = reservationTimeRepository.insertTime(reservationTimeDetails);
        return ReservationTimeResponse.from(reservationTime);
    }

    public List<ReservationTimeResponse> getTimes() {
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();
        return reservationTimes.stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public boolean deleteTimeById(long id) {
        return reservationTimeRepository.deleteTimeById(id);
    }
}
