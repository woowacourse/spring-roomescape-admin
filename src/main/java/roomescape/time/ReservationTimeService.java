package roomescape.time;

import java.util.List;

import org.springframework.stereotype.Service;
import roomescape.exception.ErrorCode;
import roomescape.exception.RoomescapeException;
import roomescape.time.dto.ReservationTimeRequest;
import roomescape.time.dto.ReservationTimeResponse;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTimeResponse create(ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = new ReservationTime(
                reservationTimeRequest.startAt()
        );

        ReservationTime saved = reservationTimeRepository.save(reservationTime);
        return ReservationTimeResponse.from(saved);
    }

    public List<ReservationTimeResponse> read() {
        return reservationTimeRepository.findAll().stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public void delete(Long id) {
        findById(id);
        reservationTimeRepository.deleteById(id);
    }

    public ReservationTime findById(Long id) {
        return reservationTimeRepository.findById(id).orElseThrow(() -> new RoomescapeException(ErrorCode.RESERVATION_TIME_NOT_FOUND));
    }
}
