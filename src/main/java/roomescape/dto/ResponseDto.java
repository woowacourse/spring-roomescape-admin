package roomescape.dto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

        public static ReservationDto of(Long id, Reservation reservation) {
            String formattedDate = reservation.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String formattedTime = reservation.getTime().format(DateTimeFormatter.ofPattern("HH:mm"));

            return new ReservationDto(id, reservation.getName(), formattedDate, formattedTime);
        }

        public static List<ReservationDto> getReservationDtos(Map<Long, Reservation> reservations) {
            List<ReservationDto> reservationDtos = new ArrayList<>();

            reservations.forEach((id, reservation) -> {
                String formattedDate = reservation.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String formattedTime = reservation.getTime().format(DateTimeFormatter.ofPattern("HH:mm"));

                reservationDtos.add(new ReservationDto(id, reservation.getName(), formattedDate, formattedTime));
            });

            return reservationDtos;
        }
    }
}
