package roomescape.service.dto;

import java.time.format.DateTimeFormatter;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDate;
import roomescape.domain.ReservationTime;

public class ReservationDto {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final String name;
    private final String date;
    private final ReservationTimeDto time;

    public ReservationDto(String name, String date, ReservationTimeDto reservationTimeDto) {
        this.name = name;
        this.date = date;
        this.time = reservationTimeDto;
    }

    public static ReservationDto from(Reservation reservation) {
        Name name = reservation.getName();
        ReservationDate date = reservation.getReservationDate();
        ReservationTime time = reservation.getReservationTime();

        return new ReservationDto(
                name.asText(),
                DATE_FORMATTER.format(date.getDate()),
                ReservationTimeDto.from(time)
        );
    }

    public Reservation toEntity(Long id) {
        return new Reservation(id, name, date, time.toEntity());
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public Long getTimeId() {
        return time.getId();
    }
}
