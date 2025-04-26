package roomescape.reservation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.request.ReservationCreateRequest;
import roomescape.reservation.dto.response.ReservationResponse;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.reservationtime.domain.ReservationTime;
import roomescape.reservationtime.repository.ReservationTimeRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(final ReservationRepository reservationRepository,
                              final ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationResponse> getReservations() {
        return reservationRepository.getAll().stream()
                .map(reservation -> {
                    ReservationTime time = reservation.getTime();
                    return ReservationResponse.from(
                            reservationRepository.getCachedId(reservation),
                            reservationTimeRepository.getCachedId(time),
                            reservation, time);
                })
                .toList();
    }

    public void delete(long id) {
        if (!reservationRepository.deleteById(id)) {
            throw new IllegalArgumentException("요청한 id와 일치하는 예약 정보가 없습니다.");
        }
    }

    public ReservationResponse create(final ReservationCreateRequest request) {
        ReservationTime time = findById(request.timeId());
        Reservation newReservation = reservationRepository.put(new Reservation(request.name(), request.date(), time));
        long newId = reservationRepository.getCachedId(newReservation);
        return ReservationResponse.from(newId, request.timeId(), newReservation, time);
    }

    public ReservationTime findById(final long id) {
        ReservationTime time = reservationTimeRepository.findById(id)
                .orElseThrow();
        reservationTimeRepository.cacheId(time, id);
        return time;
    }
}
