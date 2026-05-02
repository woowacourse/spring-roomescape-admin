package roomescape.service;

import java.time.LocalDate;
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
        validateReservationDate(request.date());

        Reservation reservation = new Reservation(request.name(), request.date(), reservationTime);
        return repository.save(reservation);
    }

    private void validateReservationDate(LocalDate date) {
        if (!date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("오늘 이후 날짜만 예약할 수 있습니다.");
        }
    }

    public void deleteById(long reservationId) {
        repository.deleteById(reservationId);
    }
}
