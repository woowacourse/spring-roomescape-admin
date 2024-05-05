package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.dto.ReservationTimeRequest;
import roomescape.service.dto.ReservationTimeResponse;

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
                reservationTimeRepository.insert(new ReservationTime(reservationTimeRequest.startAt()));

        return new ReservationTimeResponse(reservationTime.id(), reservationTime.startAt());
    }

    public void removeReservation(long id) {
        reservationTimeRepository.delete(id);
    }
}
