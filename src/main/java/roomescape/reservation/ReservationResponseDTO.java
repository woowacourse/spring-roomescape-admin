package roomescape.reservation;

import java.time.LocalDate;
import roomescape.reservationtime.ReservationTime;

public class ReservationResponseDTO {
    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    public ReservationResponseDTO(Long id, String name, LocalDate date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
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
