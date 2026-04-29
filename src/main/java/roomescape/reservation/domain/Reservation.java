package roomescape.reservation.domain;

import java.time.LocalDate;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.time.domain.ReservationTime;

public class Reservation {
    private final Long id;

    private final String name;

    private final LocalDate date;

    private final ReservationTime reservationTime;

    private Reservation(Long id, String name, LocalDate date, ReservationTime reservationTime) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.reservationTime = reservationTime;
    }

    public static Reservation create(ReservationRequestDto dto, ReservationTime reservationTime) {
        return new Reservation(null,
                dto.name(),
                dto.date(),
                reservationTime);
    }

    public static Reservation create(Long id, String name, LocalDate date, ReservationTime reservationTime) {
        return new Reservation(id,
                name,
                date,
                reservationTime);
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

    public ReservationTime getReservationTime() {
        return reservationTime;
    }
}
