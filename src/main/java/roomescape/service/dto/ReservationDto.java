package roomescape.service.dto;

import java.time.format.DateTimeFormatter;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDate;
import roomescape.domain.ReservationTime;

public class ReservationDto {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm");

    private final Long id;
    private final String name;
    private final String date;
    private final String time;

    public ReservationDto(Long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public ReservationDto(String name, String date, String time) {
        this(null, name, date, time);
    }

    public static ReservationDto from(Reservation reservation) {
        Name name = reservation.getName();
        ReservationDate date = reservation.getReservationDate();
        ReservationTime time = reservation.getReservationTime();

        return new ReservationDto(
                reservation.getId(),
                name.asText(),
                DATE_FORMATTER.format(date.getDate()),
                TIME_FORMATTER.format(time.getTime())
        );
    }

    public Reservation toEntity(Long id) {
        return new Reservation(id, name, date, time);
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
