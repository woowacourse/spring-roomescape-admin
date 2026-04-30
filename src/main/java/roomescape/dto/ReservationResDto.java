package roomescape.dto;

import java.time.LocalDate;

public class ReservationResDto {

    private Long id;
    private String name;
    private LocalDate date;
    private ReservationTimeResDto time;

    private ReservationResDto(Long id, String name, LocalDate date, ReservationTimeResDto time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationResDto from(Long id, String name, LocalDate date, ReservationTimeResDto time) {
        return new ReservationResDto(id, name, date, time);
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

    public ReservationTimeResDto getTime() {
        return time;
    }
}
