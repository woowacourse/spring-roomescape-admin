package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationDto;
import roomescape.entity.Reservation;
import roomescape.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public long addReservation(ReservationDto reservationDto) {
        return reservationRepository.insert(reservationDto);
    }

    public Reservation getReservationById(long reservationId) {
        return reservationRepository.readById(reservationId);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.readAll();
    }

    public void deleteReservation(long reservationId) {
        reservationRepository.delete(reservationId);
    }
}
