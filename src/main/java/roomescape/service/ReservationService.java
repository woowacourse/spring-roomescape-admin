package roomescape.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.controller.dto.ReservationRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public ReservationResponse addReservation(ReservationRequest reservationRequest) {
        Reservation reservation = fromRequest(reservationRequest);
        Reservation savedReservation = reservationRepository.saveReservation(reservation);
        return toResponse(savedReservation);
    }

    public List<ReservationResponse> findReservations() {
        List<Reservation> reservations = reservationRepository.findAllReservation();

        return reservations.stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public void removeReservations(long reservationId) {
        reservationRepository.deleteReservationById(reservationId);
    }

    private Reservation fromRequest(ReservationRequest reservationRequest) {
        String name = reservationRequest.name();
        LocalDate date = reservationRequest.date();
        LocalTime time = reservationRequest.time();
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        return new Reservation(name, dateTime);
    }

    private ReservationResponse toResponse(Reservation reservation) {
        return new ReservationResponse(reservation.getId(),
                reservation.getName(), reservation.getDate(), reservation.getTime());
    }
}
