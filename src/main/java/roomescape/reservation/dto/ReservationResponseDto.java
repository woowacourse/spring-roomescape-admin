package roomescape.reservation.dto;

import roomescape.reservation.domain.Reservation;
import roomescape.time.dto.ReservationTimeResponseDto;

public class ReservationResponseDto {
    private final Long id;
    private final String name;
    private final String date;
    private final ReservationTimeResponseDto time;

    private ReservationResponseDto(final Long id, final String name, final String date, final ReservationTimeResponseDto time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationResponseDto from(final Reservation reservation) {
        return new ReservationResponseDto(reservation.getId(), reservation.getName(), reservation.getDate().toString(), ReservationTimeResponseDto.from(reservation.getTime()));
    }

    public Long getId() {
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
