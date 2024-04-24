package roomescape.reservation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.RequestReservation;
import roomescape.reservation.dto.ResponseReservation;
import roomescape.reservation.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Long save(RequestReservation requestReservation) {
        Reservation reservation = new Reservation(null, requestReservation.name(),
                requestReservation.date(),
                requestReservation.time());

        return reservationRepository.save(reservation);
    }

    public List<ResponseReservation> findAll() {
        List<Reservation> reservations = reservationRepository.findAll();

        return reservations.stream()
                .map(reservation -> new ResponseReservation(reservation.getId(), reservation.getName(),
                        reservation.getDate(), reservation.getTime()))
                .toList();
    }

    public void delete(Long id) {
        reservationRepository.delete(id);
    }
}
