package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.db.ReservationRepository;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;


@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    public List<Reservation> getReservations() {
        return reservationRepository.getReservations();
    }

    public Long createReservation(@RequestBody ReservationRequest reservationRequest) {
        return reservationRepository.createReservation(reservationRequest);
    }

    public void deleteReservation(@PathVariable long id) {
        reservationRepository.deleteById(id);
    }

    public Reservation findById(final Long id) {
        return reservationRepository.findById(id);
    }

    public void deleteById(final long id) {
        reservationRepository.deleteById(id);
    }
}
