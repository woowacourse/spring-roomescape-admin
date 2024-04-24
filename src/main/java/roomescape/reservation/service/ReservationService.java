package roomescape.reservation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.dto.ReservationSaveRequest;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.time.domain.Time;
import roomescape.time.repository.TimeRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(final ReservationRepository reservationRepository, final TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public ReservationResponse save(final ReservationSaveRequest reservationSaveRequest) {
        Time time = timeRepository.findById(reservationSaveRequest.getTimeId())
                .orElseThrow(() -> new IllegalArgumentException("일치하는 시간이 없습니다."));

        Reservation reservation = reservationRepository.save(reservationSaveRequest.toReservation(time));
        return ReservationResponse.toResponse(reservation);
    }

    public ReservationResponse findById(final Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 예약이 없습니다."));
        return ReservationResponse.toResponse(reservation);
    }

    public List<ReservationResponse> findAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(ReservationResponse::toResponse)
                .toList();
    }

    public void deleteById(final Long id) {
        findById(id);
        reservationRepository.deleteById(id);
    }
}
