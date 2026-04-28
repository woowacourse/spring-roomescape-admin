package roomescape.dto;

import roomescape.domain.Reservation;

public record ReservationInfoDto(long id, String name, String date, String time) {
    public static ReservationInfoDto from(Reservation reservation) {
        return new ReservationInfoDto(reservation.id(), reservation.name(), reservation.date(), reservation.time());
    }
}
