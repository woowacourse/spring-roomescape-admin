package roomescape.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequest;
import roomescape.entity.Reservation;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository repository;

    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    public List<Reservation> getAll() {
        return repository.getAll();
    }

    public Reservation add(ReservationRequest request) {
        LocalDate date = LocalDate.parse(request.date());
        LocalTime time = LocalTime.parse(request.time());

        Reservation reservation = new Reservation(
            null,
            request.name(),
            date,
            time);

        Long savedId = repository.save(reservation);
        reservation.setId(savedId);

        return reservation;
    }

    public void deleteById(long reservationId) {
        repository.deleteById(reservationId);
    }
}
