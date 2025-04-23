package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.CreateReservationDto;
import roomescape.entity.Reservation;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(CreateReservationDto createReservationDto) {
        Long id = reservationRepository.addAndGetId(createReservationDto);
        return reservationRepository.findById(id);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
