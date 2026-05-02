package roomescape.command;

import roomescape.domain.ReservationTime;

import java.time.LocalDate;
import java.util.Objects;

public record ReservationCommand(Long id, String name, LocalDate date, Long timeId) {
    public boolean hasId(Long id) {
        return Objects.equals(this.id, id);
    }
}
