package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.response.ReservationResponseDTO;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponseDTO> getReservations() {

        List<Reservation> reservations = reservationRepository.findAllReservations();
        return convertReservationsToDTO(reservations);
    }

    private List<ReservationResponseDTO> convertReservationsToDTO(List<Reservation> reservations) {

        return reservations.stream()
            .map(reservation -> new ReservationResponseDTO(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
            ))
            .toList();
    }
}
