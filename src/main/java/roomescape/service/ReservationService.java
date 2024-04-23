package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.SaveReservationRequest;
import roomescape.dto.SaveReservationTimeRequest;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(
            final ReservationRepository reservationRepository,
            final ReservationTimeRepository reservationTimeRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    public Reservation saveReservation(final SaveReservationRequest request) {
        return reservationTimeRepository.findById(request.timeId())
                .map(request::toReservation)
                .map(reservationRepository::save)
                .orElseThrow(() -> new NoSuchElementException("해당 id의 예약 시간이 존재하지 않습니다."));
    }

    public void deleteReservation(final Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    public List<ReservationTime> getReservationTimes() {
        return reservationTimeRepository.findAll();
    }

    public ReservationTime saveReservationTime(final SaveReservationTimeRequest request) {
        return reservationTimeRepository.save(request.toReservationTime());
    }

    public void deleteReservationTime(final Long reservationTimeId) {
        reservationTimeRepository.deleteById(reservationTimeId);
    }
}
