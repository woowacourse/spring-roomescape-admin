package roomescape.service;

import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {
    private static final Long TEMPORARY_ID = null;
    private static final LocalTime TEMPORARY_START_AT = null;
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponse> readReservations() {
        return reservationRepository.readReservations()
                .stream()
                .map(ReservationResponse::of)
                .toList();
    }

    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        Reservation reservation = new Reservation(TEMPORARY_ID, reservationRequest.getName(),
                reservationRequest.getDate(),
                new ReservationTime(reservationRequest.getTimeId(), TEMPORARY_START_AT));
        Long id = reservationRepository.createReservation(reservation);
        return ReservationResponse.of(reservationRepository.readReservationById(id));
    }

    public boolean deleteReservation(Long id) {
        return id.equals(reservationRepository.deleteReservation(id));
    }
}
