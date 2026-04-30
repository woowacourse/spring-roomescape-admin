package roomescape.reservation;

import java.util.List;

import org.springframework.stereotype.Service;
import roomescape.exception.ErrorCode;
import roomescape.exception.RoomescapeException;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.time.ReservationTime;
import roomescape.time.ReservationTimeService;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeService reservationTimeService;

    public ReservationService(ReservationRepository reservationRepository, ReservationTimeService reservationTimeService) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeService = reservationTimeService;
    }

    public ReservationResponse create(ReservationRequest reservationRequest) {
        ReservationTime reservationTime = reservationTimeService.findById(reservationRequest.timeId());

        Reservation reservation = new Reservation(
                reservationRequest.name(),
                reservationRequest.date(),
                reservationTime
        );
        Reservation saved = reservationRepository.save(reservation);
        return ReservationResponse.from(saved);
    }

    public List<ReservationResponse> read() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public void delete(Long id) {
        reservationRepository.findById(id).orElseThrow(() -> new RoomescapeException(ErrorCode.RESERVATION_NOT_FOUND));
        reservationRepository.deleteById(id);
    }
}
