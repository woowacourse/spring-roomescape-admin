package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationTimeRequest;
import roomescape.controller.dto.ReservationTimeResponse;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTimeResponse> findAllReservationTime() {
        return reservationTimeRepository.list().stream()
                .map(reservationTime -> new ReservationTimeResponse(
                                reservationTime.id(),
                                reservationTime.startAt()
                        )
                )
                .toList();
    }

    public ReservationTimeResponse addReservationTime(ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime =
                reservationTimeRepository.insert(new ReservationTime(null, reservationTimeRequest.startAt()));

        return new ReservationTimeResponse(reservationTime.id(), reservationTime.startAt());
    }

    public void removeReservation(long id) {
        reservationTimeRepository.delete(id);
    }
}
