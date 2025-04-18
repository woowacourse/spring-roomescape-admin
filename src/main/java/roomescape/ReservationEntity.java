package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicLong;

public record ReservationEntity(Long id, String name, LocalDate date, LocalTime time) {
    private static final AtomicLong index = new AtomicLong(1);

    public static ReservationEntity of(String name, LocalDate date, LocalTime time) {
        return new ReservationEntity(index.getAndIncrement(), name, date, time);
    }
}
