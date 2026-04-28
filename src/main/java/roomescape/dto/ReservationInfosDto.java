package roomescape.dto;

import java.util.List;
import roomescape.domain.Reservation;

public record ReservationInfosDto(List<ReservationInfoDto> reservationInfos) {
    public static ReservationInfosDto from(List<Reservation> reservations) {
        List<ReservationInfoDto> reservationInfosDto = reservations.stream().map(ReservationInfoDto::from).toList();
        return new ReservationInfosDto(reservationInfosDto);
    }
}
