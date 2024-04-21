package roomescape.service.reservation.dto;

import java.time.LocalDate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public class ReservationRequestDto {

    private final String name;
    private final LocalDate date;
    private final long timeId;

    public ReservationRequestDto(String name, LocalDate date, long timeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public Reservation toReservation() {
        return new Reservation(null, name, date, new ReservationTime(timeId, null));
    }
}
