package roomescape.domain.reservation.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.domain.reservation.domain.Reservation;
import roomescape.domain.reservation.dto.request.ReservationCreateRequestDTO;
import roomescape.domain.reservation.dto.response.ReservationResponseDTO;
import roomescape.domain.reservation.repository.ReservationRepository;

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

    private ReservationResponseDTO convertReservationToDTO(Reservation reservation) {
        return new ReservationResponseDTO(reservation.getId(), reservation.getName(), reservation.getDate(),
            reservation.getTime());
    }

    private List<ReservationResponseDTO> convertReservationsToDTO(List<Reservation> reservations) {

        return reservations.stream()
            .map(this::convertReservationToDTO)
            .toList();
    }

    public ReservationResponseDTO saveReservation(ReservationCreateRequestDTO requestDTO) {
        Reservation reservation = new Reservation(requestDTO.name(), requestDTO.date(), requestDTO.time());
        return convertReservationToDTO(reservationRepository.save(reservation));
    }

    public void deleteReservationById(Long id) {
        reservationRepository.deleteReservationById(id);
    }
}
