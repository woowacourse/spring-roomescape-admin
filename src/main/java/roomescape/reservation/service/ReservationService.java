package roomescape.reservation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;
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

    public Reservation save(final ReservationRequest reservationRequest) {
        Time time = timeRepository.findById(reservationRequest.getTimeId())
                .orElseThrow(() -> new IllegalArgumentException("일치하는 시간이 없습니다."));

        return reservationRepository.save(reservationRequest, time);
    }

    public Reservation findById(final Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 예약이 없습니다."));
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public void deleteById(final Long id) {
        findById(id);
        reservationRepository.deleteById(id);
    }
}
