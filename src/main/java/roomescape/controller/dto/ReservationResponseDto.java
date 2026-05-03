package roomescape.controller.dto;

import roomescape.domain.Reservation;

public class ReservationResponseDto {
    private final long id;
    private final String name;
    private final String date;
    private final ReservationTimeResponseDto time;

    public ReservationResponseDto(long id, String name, String date, ReservationTimeResponseDto time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationResponseDto toDto(Reservation reservation) {
        return new ReservationResponseDto(reservation.getId(), reservation.getName(),
                reservation.getDate().getDate().toString(),
                ReservationTimeResponseDto.toDto(reservation.getTime()));
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
