package roomescape.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class Reservation {
    private final Long id;
    private final Name name;
    private final ReservationDate date;
    private final ReservationTime time;

    public Reservation(final Long id, final Name name, final ReservationDate date, final ReservationTime time) {
        validate(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation from(final Long id, final String name, final String date, final ReservationTime time) {
        return new Reservation(id, new Name(name), ReservationDate.from(date), time);
    }

    private void validate(final Name name, final ReservationDate date, final ReservationTime time) {
        try {
            Objects.requireNonNull(name, "이름이 공백입니다");
            Objects.requireNonNull(date, "날짜가 공백입니다");
            Objects.requireNonNull(time, "시간이 공백입니다");
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Long getId() {
        return id;
    }

    public String getNameAsString() {
        return name.value();
    }

    public String getDateAsString() {
        return date.valueAsString();
    }

    public ReservationTime getTime() {
        return time;
    }

    @JsonIgnore
    public String getStartAtAsString() {
        return time.getStartAt()
                   .toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private String date;
        private ReservationTime time;

        private Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder date(String date) {
            this.date = date;
            return this;
        }

        public Builder time(ReservationTime time) {
            this.time = time;
            return this;
        }

        public Reservation build() {
            return Reservation.from(id, name, date, time);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof final Reservation that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", name='" + name.value() + '\'' +
                ", date='" + +'\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
