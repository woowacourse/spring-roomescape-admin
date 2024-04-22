package roomescape.service.dto;

import java.time.format.DateTimeFormatter;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDate;
import roomescape.domain.TimeSlot;

public class ReservationCreationDto {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final String name;
    private final String date;
    private final TimeSlotDto time;

    public ReservationCreationDto(String name, String date, TimeSlotDto timeSlotDto) {
        this.name = name;
        this.date = date;
        this.time = timeSlotDto;
    }

    public static ReservationCreationDto from(Reservation reservation) {
        Name name = reservation.getName();
        ReservationDate date = reservation.getReservationDate();
        TimeSlot time = reservation.getReservationTime();

        return new ReservationCreationDto(
                name.asText(),
                DATE_FORMATTER.format(date.getDate()),
                TimeSlotDto.from(time)
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
