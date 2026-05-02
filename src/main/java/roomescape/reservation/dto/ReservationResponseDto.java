package roomescape.reservation.dto;

import roomescape.reservation.domain.Reservation;

public class ReservationResponseDto {

    private final Long id;
    private final String name;

    public ReservationResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ReservationResponseDto from(Reservation reservation) {
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getName()
        );
    }

    public static ReservationResponseDto of(Long id, Reservation reservation) {
        return new ReservationResponseDto(
                id,
                reservation.getName()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
