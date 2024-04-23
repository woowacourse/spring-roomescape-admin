package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.domain.ReservationTime;

public class ReservationTimeResponseDto {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private Long id;
    private String startAt;

    public ReservationTimeResponseDto(long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTimeResponseDto from(ReservationTime time) {
        String startAt = time.getStartAt().format(TIME_FORMATTER);
        return new ReservationTimeResponseDto(time.getId(), startAt);
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
