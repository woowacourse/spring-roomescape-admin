package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.db.ReservationRepository;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;


@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Long create(@RequestBody ReservationRequest reservationRequest) {
        return reservationRepository.createReservation(reservationRequest);
    }

    public Reservation findById(final Long id) {
        return reservationRepository.findById(id);
    }

    public void deleteById(final long id) {
        reservationRepository.deleteById(id);
    }
}
