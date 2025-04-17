package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.repositiory.ReservationRepository;
import roomescape.repositiory.ReservationRepositoryImpl;
import roomescape.domain.Reservation;
import roomescape.domain.dto.ReservationRequestDto;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService() {
        this.reservationRepository = new ReservationRepositoryImpl();
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
        return new Reservation(
                reservationDto.name(),
                reservationDto.date(),
                reservationDto.time());
    }
}
