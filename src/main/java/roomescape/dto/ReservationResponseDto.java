package roomescape.dto;

import roomescape.domain.Reservation;

public class ReservationResponseDto {
    private long id;
    private String name;
    private String date;
    private ReservationTimeResponseDto time;

    public ReservationResponseDto(long id, String name, String date, ReservationTimeResponseDto time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationResponseDto from(Reservation reservation) {
        ReservationTimeResponseDto timeResponseDto = ReservationTimeResponseDto.from(reservation.getTime());
        return new ReservationResponseDto(
                reservation.getId(), reservation.getName(), reservation.getDate(), timeResponseDto
        );
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
