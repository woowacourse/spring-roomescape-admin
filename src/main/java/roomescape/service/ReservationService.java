package roomescape.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDate;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(
            ReservationRepository reservationRepository,
            ReservationTimeRepository reservationTimeRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public Reservation create(String nameValue, LocalDate dateValue, Long timeId) {
        Name name = new Name(nameValue);
        ReservationDate date = new ReservationDate(dateValue);
        ReservationTime time = findReservationTime(timeId);

        if (reservationRepository.hasReservationAt(date, time)) {
            throw new IllegalArgumentException("[ERROR] 이미 존재하는 예약입니다.");
        }

        Reservation reservation = new Reservation(null, name, date, time);

        return reservationRepository.save(reservation);
    }

    private ReservationTime findReservationTime(Long timeId) {
        if (timeId == null || timeId <= 0) {
            throw new IllegalArgumentException("[ERROR] 예약 시간 ID는 양수여야 합니다.");
        }

        try {
            return reservationTimeRepository.findById(timeId);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 예약 시간입니다.", e);
        }
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }
}
