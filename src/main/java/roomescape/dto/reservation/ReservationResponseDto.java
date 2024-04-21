package roomescape.dto.reservation;

import roomescape.domain.Reservation;
import roomescape.dto.time.ReservationTimeResponseDto;

public class ReservationResponseDto {

    private final long id;
    private final String name;
    private final String date;
    private final ReservationTimeResponseDto time;

    public ReservationResponseDto(Reservation reservation) {
        this.id = reservation.getId();
        this.name = reservation.getName();
        this.date = reservation.getDate().toString();
        this.time = new ReservationTimeResponseDto(reservation.getTime());
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public ReservationTimeResponseDto getTime() {
        return time;
    }
}
