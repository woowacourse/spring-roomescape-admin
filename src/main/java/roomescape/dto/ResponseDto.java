package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.domain.Reservation;

public class ResponseDto {

    public static class ReservationDto {
        private Long id;
        private String name;
        private String date;
        private String time;

        private ReservationDto(Long id, String name, String date, String time) {
            this.id = id;
            this.name = name;
            this.date = date;
            this.time = time;
        }

        public Long getId() { return id; }
        public String getName() { return name; }
        public String getDate() { return date; }
        public String getTime() { return time; }

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