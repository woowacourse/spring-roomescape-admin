package roomescape;

import roomescape.entity.ReservationEntity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    public static final LocalTime START_RESERVATION_TIME = LocalTime.of(10, 0);
    public static final LocalTime LAST_RESERVATION_TIME = LocalTime.of(23, 0);

    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(final String name, final LocalDate date, final LocalTime time) {
        validateTime(time);
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validateTime(final LocalTime time) {
        if(time.isBefore(START_RESERVATION_TIME) || time.isAfter(LAST_RESERVATION_TIME)){
            throw new IllegalArgumentException("예약할 수 없는 시간입니다.");
        }
    }

    public ReservationEntity toEntity() {
        return ReservationEntity.createWithoutId(name, date, time);
    }
}
