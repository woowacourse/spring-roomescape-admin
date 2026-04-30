package roomescape.domain.reservation.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.domain.reservation.domain.Reservation;
import roomescape.domain.reservation.dto.request.ReservationCreateRequestDTO;
import roomescape.domain.reservation.dto.response.ReservationResponseDTO;
import roomescape.domain.reservation.repository.ReservationRepository;
import roomescape.domain.time.domain.Time;
import roomescape.domain.time.repository.TimeRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
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
        Reservation reservation = createReservation(requestDTO);
        return convertReservationToDTO(reservationRepository.save(reservation));
    }

    private Reservation createReservation(ReservationCreateRequestDTO requestDTO) {
        Time time = timeRepository.findTimeById(requestDTO.timeId());
        return new Reservation(requestDTO.name(), requestDTO.date(), time);
    }

    public void deleteReservationById(Long id) {
        reservationRepository.deleteReservationById(id);
    }
}
