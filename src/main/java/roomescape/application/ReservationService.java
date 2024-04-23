package roomescape.application;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private final ReservationTimeService reservationTimeService;
    private final ReservationRepository reservationRepository;

    public ReservationService(Clock clock, ReservationTimeService reservationTimeService,
                              ReservationRepository reservationRepository) {
        this.clock = clock;
        this.reservationTimeService = reservationTimeService;
        this.reservationRepository = reservationRepository;
    }

    public Reservation reserve(ReservationRequest reservationRequest) {
        ReservationTime time = reservationTimeService.findReservationTime(reservationRequest.timeId());
        validateReservationInAdvance(reservationRequest.date(), time);
        if (reservationRepository.existsByReservationDateTime(reservationRequest.date(), time.getId())) {
            throw new IllegalArgumentException("이미 예약된 날짜, 시간입니다.");
        }
        Reservation reservation = new Reservation(reservationRequest.name(), reservationRequest.date(), time);
        return reservationRepository.save(reservation);
    }

    private void validateReservationInAdvance(LocalDate date, ReservationTime time) {
        LocalDateTime reservationDateTime = LocalDateTime.of(date, time.getStartAt());
        LocalDateTime baseDateTime = LocalDateTime.now(clock).plusDays(IN_ADVANCE_RESERVATION_DAYS);
        if (reservationDateTime.isBefore(baseDateTime)) {
            throw new IllegalArgumentException(String.format("예약은 %d일 전까지 가능합니다.", IN_ADVANCE_RESERVATION_DAYS));
        }
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
