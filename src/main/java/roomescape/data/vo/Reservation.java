package roomescape.data.vo;

import java.time.LocalDate;

public class Reservation {
    private final long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;


    private Reservation(final long id, final String name, final LocalDate date, final ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
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

    public ReservationTime getTime() {
        return time;
    }

    public long getTimeId() {
        return this.time.getId();
    }

    public static class Builder {
        private long id;
        private String name;
        private LocalDate date;
        private ReservationTime time;

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

        public Builder time(final ReservationTime time) {
            this.time = time;
            return this;
        }

        public Reservation build() {
            return new Reservation(id, name, date, time);
        }
    }
}
