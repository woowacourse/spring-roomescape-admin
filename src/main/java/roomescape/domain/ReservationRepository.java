package roomescape.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;
import roomescape.dto.ReservationDto;

@Component
public class ReservationRepository {

    private static final long INITIAL_ID = 1L;

    private final AtomicLong idCount = new AtomicLong(INITIAL_ID);
    private final Map<Long, Reservation> reservations = new HashMap<>();

    public List<ReservationDto> findAll() {
        return reservations.values()
                .stream()
                .map(ReservationDto::from)
                .toList();
    }

    public ReservationDto add(final ReservationDto reservationDto) {
        long id = idCount.getAndIncrement();
        Reservation reservation = reservationDto.toEntity(id);
        reservations.put(id, reservation);
        return ReservationDto.from(reservation);
    }

    public void delete(final Long id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("id는 null일 수 없습니다.");
        }
        if (!reservations.containsKey(id)) {
            throw new IllegalArgumentException("id에 해당하는 예약을 찾을 수 없습니다.");
        }
        reservations.remove(id);
    }

    public void deleteAll() {
        idCount.set(INITIAL_ID);
        reservations.clear();
    }
}
