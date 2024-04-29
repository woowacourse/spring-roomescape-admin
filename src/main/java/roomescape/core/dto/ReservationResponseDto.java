package roomescape.core.dto;

import roomescape.core.domain.Reservation;

public class ReservationResponseDto {
    private Long id;
    private String name;
    private String date;
    private ReservationTimeResponseDto time;

    public ReservationResponseDto() {
    }

    public ReservationResponseDto(final Long id, final Reservation reservation) {
        this.id = id;
        this.name = reservation.getName();
        this.date = reservation.getDate();
        this.time = new ReservationTimeResponseDto(reservation.getReservationTime());
    }

    public ReservationResponseDto(final Reservation reservation) {
        this(reservation.getId(), reservation);
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
