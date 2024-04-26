package roomescape.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationAddRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.repository.reservation.ReservationRepository;
import roomescape.repository.reservationtime.ReservationTimeRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(
            ReservationRepository reservationRepository,
            ReservationTimeRepository reservationTimeRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationResponse addReservation(ReservationAddRequest reservationAddRequest) {
        ReservationTime reservationTime = getValidReservationTime(reservationAddRequest);

        Reservation reservation = new Reservation(
                reservationAddRequest.name(),
                reservationAddRequest.date(),
                reservationTime
        );

        return ReservationResponse.from(reservationRepository.save(reservation));
    }

    private ReservationTime getValidReservationTime(ReservationAddRequest reservationAddRequest) {
        Optional<ReservationTime> reservationTime = reservationTimeRepository.findById(reservationAddRequest.timeId());

        if (reservationTime.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 예약 시간입니다. time_id = " + reservationAddRequest.timeId());
        }

        return reservationTime.get();
    }

    public void deleteReservation(Long id) {
        reservationRepository.delete(id);
    }

    public List<ReservationResponse> getReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationResponse::from)
                .toList();
    }
}
