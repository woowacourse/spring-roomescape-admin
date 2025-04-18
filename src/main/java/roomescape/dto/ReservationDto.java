package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.entity.ReservationEntity;

public record ReservationDto(Long id, String name, String date, String time) {

    public static ReservationDto from(final ReservationEntity reservationInfo) {
        return new ReservationDto(
                reservationInfo.getId(),
                reservationInfo.getName(),
                reservationInfo.getDate().toString(),
                reservationInfo.getTime().format(DateTimeFormatter.ofPattern("HH:mm"))
        );
    }
}
