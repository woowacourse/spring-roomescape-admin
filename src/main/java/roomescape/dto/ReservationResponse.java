package roomescape.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import roomescape.model.Reservation;

public class ReservationResponse {

    private final Long id;
    private final String name;
    private final String date;
    private final String time;

    private ReservationResponse(Long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationResponse from(Reservation reservation) {
        LocalDateTime dateTime = reservation.getDateTime();
        String dateExpression = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String timeExpression = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));

        return new ReservationResponse(reservation.getId(), reservation.getName(), dateExpression, timeExpression);
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

    public String getTime() {
        return time;
    }
}
