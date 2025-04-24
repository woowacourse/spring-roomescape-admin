package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.dto.ReservationRequestDto;
import roomescape.repositiory.ReservationRepository;

@Service
public class ReservationService {

    @Autowired
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> readAll() {
        return reservationRepository.findAll();
    }

    public Reservation add(ReservationRequestDto reservationDto) {
        Reservation reservation = convertReservation(reservationDto);
        return reservationRepository.add(reservation);
    }

    public void delete(Long id) {
        Reservation reservation = reservationRepository.findById(id);
        reservationRepository.delete(reservation);
    }

    private Reservation convertReservation(ReservationRequestDto reservationDto) {
        return Reservation.of(
                reservationDto.name(),
                reservationDto.date(),
                reservationDto.time());
    }
}
