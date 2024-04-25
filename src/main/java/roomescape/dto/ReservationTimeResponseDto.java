package roomescape.dto;

import roomescape.entity.ReservationTime;

public class ReservationTimeResponseDto {

    private final Long id;
    private final String startAt;

    private ReservationTimeResponseDto(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTimeResponseDto from(ReservationTime reservationTime) {
        return new ReservationTimeResponseDto(
                reservationTime.getId(),
                reservationTime.getStartAt().toString()
        );
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", startAt='" + startAt + '\'' +
                '}';
    }
}
