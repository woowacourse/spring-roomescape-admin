package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.ReservationCreateRequest;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.reservation.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {

    private static final String RESERVATION_NOT_FOUND = "존재하지 않는 예약입니다.";

    private final ReservationRepository reservationRepository;
    private final ReservationTimeService reservationTimeService;

    public ReservationService(ReservationRepository reservationRepository, ReservationTimeService reservationTimeService) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeService = reservationTimeService;
    }

    public List<Reservation> readReservations() {
        return reservationRepository.findAll();
    }

    public Reservation readReservation(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(RESERVATION_NOT_FOUND));
    }

    public Reservation createReservation(ReservationCreateRequest request) {
        ReservationTime reservationTime = reservationTimeService.readReservationTime(request.timeId());
        Reservation reservation = request.toReservation(reservationTime);
        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
