package roomescape.data.vo;

import java.time.LocalDate;
import java.time.LocalTime;


public class Reservation {
    private final long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    private Reservation() {
        this.id = 0;
        this.name = "";
        this.date = LocalDate.now();
        this.time = LocalTime.now();
    }

    private Reservation(final long id, final String name, final LocalDate date, final LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(final long id, final Reservation reservation) {
        this.id = id;
        this.name = reservation.name;
        this.date = reservation.date;
        this.time = reservation.time;
    }

    public long getId() {
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

    public static class Builder {
        private long id;
        private String name;
        private LocalDate date;
        private LocalTime time;

        public Builder id(final long id) {
            this.id = id;
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder date(final LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder time(final LocalTime time) {
            this.time = time;
            return this;
        }

        public Reservation build() {
            return new Reservation(id, name, date, time);
        }
    }

}
