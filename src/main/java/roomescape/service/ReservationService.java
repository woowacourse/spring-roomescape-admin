package roomescape.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationService {

    private final ReservationRepository repository;
    private final ReservationTimeRepository timeRepository;

    public ReservationService(ReservationRepository repository, ReservationTimeRepository timeRepository) {
        this.repository = repository;
        this.timeRepository = timeRepository;
    }

    public List<Reservation> getAll() {
        return repository.getAll();
    }

    public Reservation add(ReservationRequest request) {
        ReservationTime reservationTime = timeRepository.findById(request.timeId())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 시간입니다. timeId: " + request.timeId()));

        LocalDate date;
        try {
            date = LocalDate.parse(request.date());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("올바르지 않은 날짜 형식입니다. (yyyy-MM-dd) date: " + request.date());
        }

        Reservation reservation = new Reservation(null, request.name(), date, reservationTime);
        return repository.save(reservation);
    }

    public void deleteById(long reservationId) {
        repository.deleteById(reservationId);
    }
}
