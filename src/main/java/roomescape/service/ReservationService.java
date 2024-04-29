package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.model.ReservationDto;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;

    }

    public List<ReservationDto> findAllReservations() {
        return reservationRepository.findAllReservations();
    }

    public ReservationDto createReservation(final ReservationDto reservationDto) {
        return reservationRepository.createReservation(reservationDto);
    }

    public void deleteReservation(final Long id) {
        reservationRepository.deleteReservation(id);
    }

}
