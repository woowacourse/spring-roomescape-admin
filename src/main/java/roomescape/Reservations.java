package roomescape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class Reservations {

    private final List<Reservation> reservations = new ArrayList<>();

    public void add(final Reservation reservation) {
        validateUnique(reservation.getId());
        reservations.add(reservation);
    }

    private void validateUnique(final Long id) {
        final boolean isUnique = reservations.stream().anyMatch(reservation -> reservation.getId().equals(id));
        if (isUnique) {
            throw new IllegalStateException("이미 존재하는 ID입니다.");
        }
    }

    public void removeById(final Long id) {
        reservations.remove(findById(id));
    }

    public Reservation findById(final Long id) {
        return reservations.stream()
                .filter(rs -> rs.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID 입니다."));
    }

    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(reservations);
    }
}
