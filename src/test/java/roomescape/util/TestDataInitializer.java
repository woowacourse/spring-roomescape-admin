package roomescape.util;

import java.time.LocalTime;
import org.springframework.stereotype.Component;
import roomescape.time.domain.ReservationTime;
import roomescape.time.repository.ReservationTimeRepository;

@Component
public class TestDataInitializer {

    private final ReservationTimeRepository reservationTimeRepository;

    public TestDataInitializer(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public void initializeReservationTime(LocalTime localTime) {
        reservationTimeRepository.save(new ReservationTime(localTime.withSecond(0).withNano(0)));
    }
}
