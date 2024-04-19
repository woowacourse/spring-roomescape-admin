package roomescape.domain.reservation;

import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;

public class Reservation {

    private final Long id;
    private final Name name;
    private final ReservationDate date;
    private final ReservationTime time;

    public Reservation(Long id, Name name, ReservationDate date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(Long id, ReservationRequestDto reservationRequestDto) {
        this(id, new Name(reservationRequestDto.name()), new ReservationDate(reservationRequestDto.date()), new ReservationTime(reservationRequestDto.time()));
    }

    public ReservationResponseDto toResponseDto() {
        return new ReservationResponseDto(id, name.getName(), date.getDate(), time.getTime());
    }

    public Long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public ReservationDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }
}
