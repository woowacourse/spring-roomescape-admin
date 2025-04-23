package roomescape.domain;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Reservations {

    private final Map<Long, Reservation> reservations;
    private final Counter counter;

    public Reservations(final List<Reservation> reservations,
                        final Counter counter) {
        final Map<Long, Reservation> reservationById = reservations.stream()
                .collect(Collectors.toMap(Reservation::getId, Function.identity(), (existing, replacement) -> existing,
                        ConcurrentHashMap::new));
        this.reservations = reservationById;
        this.counter = counter;
    }

    public Reservations() {
        this.reservations = new ConcurrentHashMap<>();
        this.counter = new Counter();
    }

    public Reservation addReservation(final String name, final LocalDateTime dateTime) {
        final long id = counter.getAndIncrease();
        final Reservation reservation = new Reservation(id, name, dateTime.toLocalDate(), dateTime.toLocalTime());
        reservations.put(id, reservation);
        return reservation;
    }

    public void deleteById(final Long id) {
        if (!reservations.containsKey(id)) {
            throw new IllegalArgumentException("[ERROR] 해당 id가 없습니다.");
        }
        reservations.remove(id);
    }

    public Map<Long, Reservation> getReservations() {
        return Collections.unmodifiableMap(reservations);
    }
}
