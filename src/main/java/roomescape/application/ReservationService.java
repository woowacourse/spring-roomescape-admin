package roomescape.application;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservationDateTime;
import roomescape.domain.reservation.repository.ReservationRepository;
import roomescape.dto.reservation.ReservationRequest;

@Service
public class ReservationService {
    private final Clock clock;
    private final ReservationRepository reservationRepository;

    public ReservationService(Clock clock, ReservationRepository reservationRepository) {
        this.clock = clock;
        this.reservationRepository = reservationRepository;
    }

    public Reservation reserve(ReservationRequest reservationRequest) {
        ReservationDateTime reservationDateTime = createReservationDateTime(reservationRequest);
        if (reservationRepository.existsByReservationDateTime(reservationDateTime)) {
            throw new IllegalArgumentException("이미 예약된 날짜, 시간입니다.");
        }
        Reservation reservation = new Reservation(reservationRequest.name(), reservationDateTime);
        return reservationRepository.save(reservation);
    }

    private ReservationDateTime createReservationDateTime(ReservationRequest reservationRequest) {
        LocalDate date = reservationRequest.date();
        LocalTime time = reservationRequest.time();
        return new ReservationDateTime(date, time, clock);
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
