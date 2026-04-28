package roomescape.dto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResponseDto {

    public static class Reservation {
        private Long id;
        private String name;
        private String date;
        private String time;

        private Reservation(Long id, String name, String date, String time) {
            this.id = id;
            this.name = name;
            this.date = date;
            this.time = time;
        }

        public static List<Reservation> getReservationDtos(Map<Long, roomescape.domain.Reservation> reservations) {
            List<Reservation> reservationDtos = new ArrayList<>();

            reservations.forEach((id, reservation) -> {
                String formattedDate = reservation.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String formattedTime = reservation.getTime().format(DateTimeFormatter.ofPattern("mm:ss"));

                reservationDtos.add(new Reservation(id, reservation.getName(), formattedDate, formattedTime));
            });

            return reservationDtos;
        }
    }
}
