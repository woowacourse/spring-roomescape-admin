package roomescape.reservation.domain;

import java.time.LocalDate;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import roomescape.reservationtime.domain.ReservationTime;

@Builder
@Getter
@EqualsAndHashCode(of = "id")
public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    public Long getTimeId() {
        return time.getId();
    }

    public Reservation withId(Long generatedId) {
        return Reservation.builder()
                .id(generatedId)
                .name(this.name)
                .date(this.date)
                .time(this.time)
                .build();
    }
}
