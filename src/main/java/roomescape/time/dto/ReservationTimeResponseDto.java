package roomescape.time.dto;

import roomescape.time.entity.ReservationTime;

public class ReservationTimeResponseDto {

    private final Long id;
    private final String startAt;

    public ReservationTimeResponseDto(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTimeResponseDto from(ReservationTime time) {
        return new ReservationTimeResponseDto(time.getId(), time.getStartAt());
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
