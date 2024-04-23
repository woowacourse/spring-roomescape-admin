package roomescape.application;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.repository.ReservationRepository;
import roomescape.domain.time.ReservationTime;
import roomescape.dto.reservation.ReservationRequest;

@Service
public class ReservationService {
    private static final int IN_ADVANCE_RESERVATION_DAYS = 1;

    private final Clock clock;
    private final ReservationRepository reservationRepository;

    public ReservationService(Clock clock, ReservationRepository reservationRepository) {
        this.clock = clock;
        this.reservationRepository = reservationRepository;
    }

    public Reservation reserve(ReservationRequest reservationRequest) {
        LocalDate date = reservationRequest.date();
        // todo date, time -> clock 검증
        ReservationTime time = new ReservationTime(reservationRequest.time()); // todo request의 time으로 timeservice 호출
        if (reservationRepository.existsByReservationDateTime(date, time.getId())) {
            throw new IllegalArgumentException("이미 예약된 날짜, 시간입니다.");
        }
        Reservation reservation = new Reservation(reservationRequest.name(), date, time);
        return reservationRepository.save(reservation);
    }

    public List<Reservation> findReservations() {
        return reservationRepository.findAll();
    }

    public void cancel(long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 예약입니다.");
        }
        reservationRepository.deleteById(id);
    }
}
