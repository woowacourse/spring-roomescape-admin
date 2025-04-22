package roomescape.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.http.HttpStatus;
import roomescape.exception.CustomException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicLong;

public class Reservation {

    private final static AtomicLong ID_GENERATOR = new AtomicLong(0);

    private final Long id;

    private final String name;

    private final LocalDate date;

    private final LocalTime time;

    @JsonCreator
    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private Reservation(String name, LocalDate date, LocalTime time) {
        this.id = ID_GENERATOR.incrementAndGet();
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation of(String name, LocalDate date, LocalTime time) {
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        validateTense(dateTime);
        return new Reservation(name, date, time);
    }

    private static void validateTense(LocalDateTime dateTime) {
        if (isPastTense(dateTime)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "과거시점으로 예약을 진행할 수 없습니다.");
        }
    }

    private static boolean isPastTense(LocalDateTime dateTime) {
        LocalDateTime now = LocalDateTime.now();
        return dateTime.isBefore(now);
    }

    public boolean isSameDateTime(Reservation compare) {
        return this.getDateTime().isEqual(compare.getDateTime());
    }

    public LocalDateTime getDateTime() {
        return LocalDateTime.of(date, time);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
