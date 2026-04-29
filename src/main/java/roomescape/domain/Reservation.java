package roomescape.domain;

import java.util.Objects;
import org.springframework.util.StringUtils;

public class Reservation {
    private final Long id;
    private final String name;
    private final String date;
    private final ReservationTime time;

    private Reservation(
            Long id,
            String name,
            String date,
            ReservationTime time
    ) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation create(
            String name,
            String date,
            ReservationTime time
    ) {
        validateName(name);
        validateDate(date);
        validateTime(time);

        return new Reservation(
                null,
                name,
                date,
                time
        );
    }

    public static Reservation retrieve(
            long id,
            String name,
            String date,
            ReservationTime time
    ) {
        return new Reservation(
                id,
                name,
                date,
                time
        );
    }

    public Reservation withId(long id) {
        return new Reservation(
                id,
                this.name,
                this.date,
                this.time
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }

    public long getTimeId() {
        return time.getId();
    }

    private static void validateName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("예약엔 이름이 존재해야 합니다.");
        }
    }

    private static void validateDate(String date) {
        if (date == null) {
            throw new IllegalArgumentException("예약엔 날짜가 존재해야 합니다.");
        }
    }

    private static void validateTime(ReservationTime time) {
        if (time == null) {
            throw new IllegalArgumentException("예약엔 시간이 존재해야 합니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name)
                && Objects.equals(date, that.date) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, date, time);
    }
}
