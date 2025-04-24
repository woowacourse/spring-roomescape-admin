package roomescape.reservation;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.common.repository.AbstractRepository;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.request.ReservationCreateRequest;
import roomescape.reservation.dto.response.ReservationResponse;
import roomescape.reservationtime.domain.ReservationTime;

@Service
public class ReservationService {

    private final AbstractRepository<Reservation> reservationRepository;
    private final AbstractRepository<ReservationTime> reservationTimeRepository;

    public ReservationService(final AbstractRepository<Reservation> reservationRepository,
                              final AbstractRepository<ReservationTime> reservationTimeRepository) {
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
        reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("요청한 id와 일치하는 예약 정보가 없습니다."));
        reservationRepository.deleteById(id);
    }

    public ReservationResponse create(final ReservationCreateRequest request) {
        ReservationTime time = reservationTimeRepository.findById(request.timeId())
                .orElseThrow();
        reservationTimeRepository.cacheId(time, request.timeId());
        Reservation newReservation = reservationRepository.put(new Reservation(request.name(), request.date(), time));
        long newId = reservationRepository.getCachedId(newReservation);
        return ReservationResponse.from(newId, request.timeId(), newReservation, time);
    }
}
