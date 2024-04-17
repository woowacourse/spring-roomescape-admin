package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import roomescape.entity.ReservationEntity;

public class ReservationRequestDto {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private final String name;
    private final String date;
    private final String time;

    public ReservationRequestDto(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public ReservationEntity toEntity() {
        LocalDate localDate = LocalDate.parse(date, dateFormatter);
        LocalTime localTime = LocalTime.parse(time, timeFormatter);
        return new ReservationEntity(name, localDate, localTime);
    }
}
