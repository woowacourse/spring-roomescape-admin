package roomescape.dto;

import java.time.LocalTime;

public class ReservationTimeResDto {

    private Long id;
    private LocalTime startAt;

    private ReservationTimeResDto(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTimeResDto from(Long id, LocalTime startAt) {
        return new ReservationTimeResDto(id, startAt);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
