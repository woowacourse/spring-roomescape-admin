package roomescape.dto;

import roomescape.entity.Reservation;

public class ReservationResponseDto {

    private final Long id;
    private final String name;
    private final String date;
    private final ReservationTimeResponseDto time;

    private ReservationResponseDto(Long id, String name, String date, ReservationTimeResponseDto time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationResponseDto from(Reservation reservation) {
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate().toString(),
                ReservationTimeResponseDto.from(reservation.getReservationTime())
        );
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
