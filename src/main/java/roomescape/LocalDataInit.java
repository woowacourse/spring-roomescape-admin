package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

@RequiredArgsConstructor
public class LocalDataInit {

    private final ReservationRepository reservationRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        reservationRepository.save(new Reservation("브라운", LocalDate.now(), LocalTime.now()));
        reservationRepository.save(new Reservation("솔라", LocalDate.now().plusDays(1), LocalTime.now()));
        reservationRepository.save(new Reservation("브리", LocalDate.now().plusDays(2), LocalTime.now()));
    }
}
