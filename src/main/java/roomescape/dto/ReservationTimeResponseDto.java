package roomescape.dto;

import roomescape.domain.ReservationTime;

public class ReservationTimeResponseDto {
    private long id;
    private String startAt;

    public ReservationTimeResponseDto(long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTimeResponseDto from(ReservationTime time) {
        return new ReservationTimeResponseDto(time.getId(), time.getStartAt());
    }

    public long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
