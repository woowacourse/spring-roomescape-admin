package roomescape.service.dto;

import java.time.LocalDate;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDate;
import roomescape.domain.TimeSlot;

public class ReservationCreationDto {

    private final String name;
    private final LocalDate date;
    private final TimeSlotDto timeSlotDto;

    public ReservationCreationDto(String name, LocalDate date, TimeSlotDto timeSlotDto) {
        this.name = name;
        this.date = date;
        this.timeSlotDto = timeSlotDto;
    }

    public ReservationCreationDto(String name, String date, TimeSlotDto timeSlotDto) {
        this(name, LocalDate.parse(date), timeSlotDto);
    }

    public static ReservationCreationDto from(Reservation reservation) {
        Name name = reservation.getName();
        ReservationDate date = reservation.getReservationDate();
        TimeSlot timeSlot = reservation.getTimeSlot();

        return new ReservationCreationDto(
                name.asText(),
                date.getDate(),
                TimeSlotDto.from(timeSlot)
        );
    }

    public Reservation toEntity(Long id) {
        return new Reservation(id, name, date, timeSlotDto.toEntity());
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public Long getTimeId() {
        return timeSlotDto.getId();
    }
}
