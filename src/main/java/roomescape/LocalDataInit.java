package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@RequiredArgsConstructor
public class LocalDataInit {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository timeRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        ReservationTime time1 = timeRepository.save(new ReservationTime(LocalTime.now()));
        ReservationTime time2 = timeRepository.save(new ReservationTime(LocalTime.now().plusHours(1)));

        reservationRepository.save(new Reservation("브라운", LocalDate.now().plusDays(1), time1));
        reservationRepository.save(new Reservation("솔라", LocalDate.now().plusDays(2), time1));
        reservationRepository.save(new Reservation("브리", LocalDate.now().plusDays(3), time2));
    }
}
