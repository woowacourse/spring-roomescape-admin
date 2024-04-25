package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.repository.ReservationRepository;
import roomescape.domain.time.Time;
import roomescape.domain.time.repository.TimeRepository;
import roomescape.dto.reservation.ReservationRequest;
import roomescape.dto.reservation.ReservationResponse;
import roomescape.dto.time.TimeRequest;
import roomescape.dto.time.TimeResponse;

@Service
public class ReservationTimeService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationTimeService(ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public List<ReservationResponse> findAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(reservation -> ReservationResponse.from(reservation))
                .toList();
    }

    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        Long timeId = reservationRequest.timeId();
        Time time = timeRepository.findById(timeId);

        Reservation requestReservation = reservationRequest.toReservation(time);
        Reservation responseReservation = reservationRepository.create(requestReservation);

        return ReservationResponse.from(responseReservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.delete(id);
    }

    public List<TimeResponse> findAllTimes() {
        return timeRepository.findAll()
                .stream()
                .map(time -> TimeResponse.from(time))
                .toList();
    }

    public TimeResponse createTime(TimeRequest timeRequest) {
        Time requestTime = timeRequest.toTime();
        Time responseTime = timeRepository.create(requestTime);

        return TimeResponse.from(responseTime);
    }

    public void deleteTime(Long id) {
        timeRepository.delete(id);
    }
}
