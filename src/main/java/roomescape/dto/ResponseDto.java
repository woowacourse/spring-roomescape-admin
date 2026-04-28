package roomescape.dto;

import java.time.format.DateTimeFormatter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import roomescape.domain.Reservation;

public class ResponseDto {

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ReservationDto {
        private Long id;
        private String name;
        private String date;
        private String time;

        public static ReservationDto of(Reservation reservation) {
            return new ReservationDto(
                    reservation.getId(),
                    reservation.getName(),
                    reservation.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    reservation.getTime().format(DateTimeFormatter.ofPattern("HH:mm"))
            );
        }
    }
}