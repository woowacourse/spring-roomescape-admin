package roomescape.domain;

import roomescape.dto.ReservationRequestDto;

import java.time.LocalDate;
import java.util.Objects;

public class Reservation {

    private Long id;
    private String name;
    private LocalDate date;
    private ReservationTime time;

    private Reservation() {
    }

    public Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(ReservationRequestDto reservation, ReservationTime time) {
        this(null, reservation.getName(), reservation.getDate(), time);
    }

    public static Reservation toEntity(long id, Reservation reservation) {
        return new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getTime());
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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Reservation other)) {
            return false;
        }
        return id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
