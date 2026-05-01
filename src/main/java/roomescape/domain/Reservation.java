package roomescape.domain;

import java.time.LocalDate;

public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private ReservationTime time;

    public Reservation() {
    }

    public Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation create(Long id, String name, LocalDate date, ReservationTime time) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("예약자 이름은 필수입니다.");
        }
        if (date == null) {
            throw new IllegalArgumentException("예약 날짜는 필수입니다.");
        }
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("과거 날짜로 예약할 수 없습니다.");
        }
        if (time == null) {
            throw new IllegalArgumentException("예약 시간은 필수입니다.");
        }
        return new Reservation(id, name, date, time);
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

    public ReservationTime getTime() {
        return time;
    }
}
