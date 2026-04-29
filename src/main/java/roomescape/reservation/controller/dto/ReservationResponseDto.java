package roomescape.reservation.controller.dto;

import roomescape.reservation.domain.Reservation;

public class ReservationResponseDto {
    private final Long id;
    private final String name;
    private final String date;
    private final String time;

    public ReservationResponseDto(Long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationResponseDto from(Reservation reservation) {
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime() == null ? null : reservation.getTime().getStartAt()
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

    public String getTime() {
        return time;
    }
}
