package roomescape.reservation.service.fake;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.entity.Reservation;
import roomescape.reservation.entity.ReservationTime;
import roomescape.common.exception.EntityNotFoundException;
import roomescape.reservation.service.ReservationService;

public class FakeReservationService implements ReservationService {

    private static final Long INITIAL_ID = 1L;

    private final AtomicLong id = new AtomicLong(INITIAL_ID);
    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();
    private final Map<Long, ReservationTime> reservationTimes = new ConcurrentHashMap<>();

    @Override
    public List<ReservationResponse> getAll() {
        return reservations.values()
                .stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @Override
    public ReservationResponse save(ReservationRequest request) {
        if (!reservationTimes.containsKey(request.timeId())){
            throw new EntityNotFoundException("Time not found id = " + request.timeId());
        }
        ReservationTime reservationTime = reservationTimes.get(request.timeId());

        long id = this.id.getAndIncrement();
        Reservation reservation = new Reservation(id, request.name(), request.date(),
                reservationTime);

        reservations.put(id, reservation);

        return ReservationResponse.from(reservation);
    }

    @Override
    public void delete(Long id) {
        if (!reservations.containsKey(id)) {
            throw new EntityNotFoundException("Reservation with id " + id + " not found");
        }

        reservations.remove(id);
    }

    public void addReservation(Long id, Reservation reservation){
        reservations.put(id, reservation);
    }

    public void addReservationTime(Long id, ReservationTime reservationTime){
        reservationTimes.put(id, reservationTime);
    }
}
