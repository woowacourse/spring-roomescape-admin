package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.controller.ReservationCreateRequest;
import roomescape.entity.Reservation;
import roomescape.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation addReservation(final ReservationCreateRequest reservationCreateRequest) {
        Long id = reservationRepository.add(reservationCreateRequest);

        return reservationRepository.findById(id);
    }

    public Reservation getReservationById(final Long id) {
        return reservationRepository.findById(id);
    }

    public void deleteReservationById(final Long id) {
        reservationRepository.removeById(id);
    }
}
