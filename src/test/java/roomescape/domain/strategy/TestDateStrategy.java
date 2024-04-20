package roomescape.domain.strategy;

import java.time.LocalDate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class TestDateStrategy implements ReservationDateStrategy {

    @Override
    public boolean isInvalid(LocalDate date) {
        return false;
    }
}
