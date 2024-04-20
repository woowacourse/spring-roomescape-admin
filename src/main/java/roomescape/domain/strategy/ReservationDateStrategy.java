package roomescape.domain.strategy;

import java.time.LocalDate;

public interface ReservationDateStrategy {

    boolean isInvalid(LocalDate date);
}
