package roomescape.reservation.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import roomescape.reservation.controller.request.ReservationCreateRequest;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.reservation.service.exception.ReservationNotFoundException;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> findReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(ReservationCreateRequest request) {
        Reservation reservation = new Reservation(
                request.name(),
                LocalDateTime.of(LocalDate.parse(request.date()), LocalTime.parse(request.time()))
        );
        return reservationRepository.save(reservation);
    }

    public void delete(Long id) {
        Reservation reservation = findReservation(id);
        reservationRepository.deleteById(reservation.getId());
    }

    private Reservation findReservation(Long id) {
        Optional<Reservation> found = reservationRepository.findById(id);

        if (found.isEmpty()) {
            throw new ReservationNotFoundException("[ERROR] 예약을 찾을 수 없습니다.");
        }

        return found.get();
    }
}
