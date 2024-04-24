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

    public long createReservation(ReservationDto reservationDto) {
        return reservationRepository.create(reservationDto);
    }

    public Reservation readReservation(long reservationId) {
        return reservationRepository.read(reservationId);
    }

    public List<Reservation> readAllReservation() {
        return reservationRepository.readAll();
    }

    public void deleteReservation(long reservationId) {
        reservationRepository.delete(reservationId);
    }
}
