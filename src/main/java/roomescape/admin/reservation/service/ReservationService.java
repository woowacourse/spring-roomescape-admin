package roomescape.admin.reservation.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.admin.reservation.controller.dto.request.ReservationRequest;
import roomescape.admin.reservation.entity.Reservation;
import roomescape.admin.reservation.entity.ReservationTime;
import roomescape.admin.reservation.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation create(ReservationRequest reservationRequest) {
        Reservation reservation = new Reservation(null, reservationRequest.name(), reservationRequest.date(),
                new ReservationTime(reservationRequest.timeId(), null));
        return reservationRepository.save(reservation);
    }

    public int delete(Long id) {
        return reservationRepository.delete(id);
    }
}
