package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Reservation {
    private final Long id;
    private final String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate date;
    @JsonFormat(pattern = "HH:mm")
    private final LocalTime time;

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        validate(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation generateWithPrimaryKey(Reservation reservation, Long newPrimaryKey) {
        return new Reservation(newPrimaryKey, reservation.name, reservation.date, reservation.time);
    }

    public boolean isSameId(Long id) {
        return Objects.equals(this.id, id);
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

    private void validate(String name, LocalDate date, LocalTime time) {
        if (name == null || date == null || time == null) {
            throw new NullPointerException("예약 정보가 비어있습니다.");
        }
        if (name.isBlank()) {
            throw new IllegalArgumentException("예약자명은 비워둘 수 없습니다.");
        }
    }
}
