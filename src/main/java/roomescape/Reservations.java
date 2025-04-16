package roomescape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class Reservations {

    private final List<Reservation> reservations = new ArrayList<>();

    public void add(final Reservation reservation) {
        reservations.add(reservation);
    }

    public void removeById(final Long id) {
        reservations.remove(findById(id));
    }

    public Reservation findById(final Long id) {
        return reservations.stream()
                .filter(rs -> Objects.equals(rs.getId(), id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID 입니다."));
    }

    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(reservations);
    }
}
