package roomescape;

import java.time.LocalDateTime;

public record CreateReservationRequest(String date, String name, String time) {

    public Reservation to(final long id) {
        LocalDateTime localDateTime = CustomDateTimeFormatter.getLocalDateTime(this.date, this.time);
        return new Reservation(id, this.name, localDateTime);
    }
}
