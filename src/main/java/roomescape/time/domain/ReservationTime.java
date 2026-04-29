package roomescape.time.domain;

import java.time.LocalTime;
import roomescape.time.dto.ReservationTimeRequestDto;

public class ReservationTime {

    private final Long id;

    private final LocalTime time;

    private ReservationTime(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public static ReservationTime create(ReservationTimeRequestDto reservationTimeRequestDto) {
        return new ReservationTime(null, reservationTimeRequestDto.time());
    }

    public static ReservationTime create(Long id, LocalTime time) {
        return new ReservationTime(id, time);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
