package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationSaveDto {

    private final String name;
    private final LocalDate date;
    private final Long timeId;
    private final LocalTime startAt;

    public ReservationSaveDto(String name, LocalDate date, Long timeId, LocalTime startAt) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
        this.startAt = startAt;
    }

    public static ReservationSaveDto from(ReservationRequestDto requestDto, LocalTime startAt) {
        return new ReservationSaveDto(
                requestDto.getName(), requestDto.getDate(), requestDto.getTimeId(), startAt
        );
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public Long getTimeId() {
        return timeId;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
