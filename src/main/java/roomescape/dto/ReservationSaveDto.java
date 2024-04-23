package roomescape.dto;

import java.time.LocalDate;

public class ReservationSaveDto {

    private final String name;
    private final LocalDate date;
    private final Long timeId;

    public ReservationSaveDto(String name, LocalDate date, Long timeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public static ReservationSaveDto from(ReservationRequestDto requestDto) {
        return new ReservationSaveDto(requestDto.getName(), requestDto.getDate(), requestDto.getTimeId());
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
}
