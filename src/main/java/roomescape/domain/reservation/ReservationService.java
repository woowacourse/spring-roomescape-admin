package roomescape.domain.reservation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import roomescape.dto.ReservationDto;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation create(ReservationDto reservationDto) {
        return reservationRepository.save(reservationDto);
    }

    public List<Reservation> findReservations() {
        return reservationRepository.findAll();
    }

    public void cancel(Long id) {
        reservationRepository.deleteById(id);
    }
}
