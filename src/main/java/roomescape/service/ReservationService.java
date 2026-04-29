package roomescape.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequest;
import roomescape.entity.Reservation;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1);

    private final ReservationRepository repository;

    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    public List<Reservation> getReservations() {
        return repository.getAll();
    }

    public Reservation add(ReservationRequest request) {
        LocalDate date = LocalDate.parse(request.date());
        LocalTime time = LocalTime.parse(request.time());

        Reservation reservation = new Reservation(
            nextId.getAndIncrement(),
            request.name(),
            date,
            time);

        reservations.add(reservation);

        return reservation;
    }

    public void delete(Long reservationId) {
        Reservation saved = reservations.stream()
            .filter(reservation -> reservation.getId().equals(reservationId))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 예약 아이디 입니다. reservationId: " + reservationId));

        reservations.remove(saved);
    }
}
