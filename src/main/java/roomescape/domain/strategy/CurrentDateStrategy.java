package roomescape.domain.strategy;

import java.time.LocalDate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class CurrentDateStrategy implements ReservationDateStrategy {

    @Override
    public boolean isInvalid(LocalDate date) {
        return date.isBefore(LocalDate.now());
    }
}
