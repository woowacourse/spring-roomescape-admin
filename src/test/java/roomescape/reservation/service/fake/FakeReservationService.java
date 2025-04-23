package roomescape.reservation.service.fake;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;
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
    public List<ReservationResponseDto> getAll() {
        return reservations.values()
                .stream()
                .map(ReservationResponseDto::from)
                .toList();
    }

    @Override
    public ReservationResponseDto save(ReservationRequestDto requestDto) {
        if (!reservationTimes.containsKey(requestDto.timeId())){
            throw new EntityNotFoundException("Time not found id = " + requestDto.timeId());
        }
        ReservationTime reservationTime = reservationTimes.get(requestDto.timeId());

        long id = this.id.getAndIncrement();
        Reservation reservation = new Reservation(id, requestDto.name(), requestDto.date(),
                reservationTime);

        reservations.put(id, reservation);

        return ReservationResponseDto.from(reservation);
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
