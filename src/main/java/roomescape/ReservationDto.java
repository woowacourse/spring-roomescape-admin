package roomescape;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationDto {

    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public ReservationDto(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public ReservationDto(Long id, ReservationDto reservationDto) {
        this.id = id;
        this.name = reservationDto.name;
        this.date = reservationDto.date;
        this.time = reservationDto.time;
    }

    private ReservationDto() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    @JsonFormat(pattern = "HH:mm")
    public LocalTime getTime() {
        return time;
    }
}
